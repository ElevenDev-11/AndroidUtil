package cn.elevendev.utils;

import android.os.Handler;
import android.os.Looper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpUtil {
    
    private static String baseUrl = "";
    private static final OkHttpClient client = new OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build();
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());
    
    public static void setUrl(String url) {
        if (!url.endsWith("/")) {
            url += "/";
        }
        baseUrl = url;
    }
    
    public static String getUrl() {
        return baseUrl;
    }

    /**
     * 发送 POST 请求并在后台线程中执行
     *
     * @param api      请求的 API 端点
     * @param params   提交的参数
     * @param callback 请求结果的回调接口（UI线程回调）
     */
    public static void post(final String api, final Map<String, String> params, final HttpCallback callback) {
        final String url = baseUrl + api;
        new Thread(new Runnable() {
                @Override
                public void run() {
                    FormBody.Builder formBuilder = new FormBody.Builder();
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        formBuilder.add(entry.getKey(), entry.getValue());
                    }
                    RequestBody requestBody = formBuilder.build();

                    Response response = null;
                    try {
                        Request request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();

                        response = client.newCall(request).execute();

                        if (response.isSuccessful()) {
                            final String responseBody = response.body().string();
                            mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onSuccess(responseBody);
                                    }
                                });
                        } else {
                            mainHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onFailure("网络异常");
                                    }
                                });
                        }
                    } catch (final IOException e) {
                        e.printStackTrace();
                        mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    String message = e.getMessage().equals("timeout") ? "请求超时" : "网络异常";
                                    callback.onFailure("请求失败");
                                }
                            });
                    } finally {
                        if (response != null) {
                            response.close();
                        }
                    }
                }
            }).start();
    }

    /**
     * 发送 GET 请求并在后台线程中执行
     *
     * @param api      请求的 API 端点
     * @param params   提交的参数
     * @param callback 请求结果的回调接口（UI线程回调）
     */
    public static void get(final String api, final HttpCallback callback) {
		final String url = baseUrl + api;

		new Thread(new Runnable() {
				@Override
				public void run() {
					Response response = null;
					try {
						Request request = new Request.Builder()
							.url(url)
							.get()
							.build();

						response = client.newCall(request).execute();

						if (response.isSuccessful()) {
							final String responseBody = response.body().string();
							mainHandler.post(new Runnable() {
									@Override
									public void run() {
										callback.onSuccess(responseBody);
									}
								});
						} else {
							mainHandler.post(new Runnable() {
									@Override
									public void run() {
										callback.onFailure("网络异常");
									}
								});
						}
					} catch (final IOException e) {
						e.printStackTrace();
						mainHandler.post(new Runnable() {
								@Override
								public void run() {
									String message = e.getMessage().equals("timeout") ? "请求超时" : "网络异常";
									callback.onFailure(message);
								}
							});
					} finally {
						if (response != null) {
							response.close();
						}
					}
				}
			}).start();
	}
    
    
    /**
     * 下载文件并保存到指定路径
     *
     * @param url             文件的下载链接
     * @param destinationPath 文件保存的目标路径
     * @param callback        下载进度及结果的回调接口
     */
    public static void downloadFile(String url, String destinationPath, HttpCallback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure("Download failed: " + e.getMessage()); // 网络请求失败时回调
            }
                
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onFailure("Download failed: Network error, code " + response.code());
                    return;
                }

                ResponseBody body = response.body();
                if (body != null) {
                    try {
                        long fileSize = body.contentLength();
                        File destinationFile = new File(destinationPath);
                        InputStream inputStream = body.byteStream();
                        FileOutputStream outputStream = new FileOutputStream(destinationFile);
                        byte[] buffer = new byte[2048];
                        int bytesRead;
                        long downloadedBytes = 0;

                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                            downloadedBytes += bytesRead;
                            int progress = (int) ((downloadedBytes * 100) / fileSize);
                                
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onProgress(progress);
                                }
                            });
                        }

                        outputStream.close();
                        inputStream.close();
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccess("下载成功");
                            }
                        });
                    } catch (IOException e) {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailure("下载失败");
                            }
                        });
                    }
                } else {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure("网络异常");
                        }
                    });
                }
            }
        });
    }


    /**
     * 请求结果回调接口
     */
    public interface HttpCallback {
        void onSuccess(String response);
        default void onFailure(String errorMessage) {
            // 请求失败
        }
        default void onProgress(int progress) {
            // 进度更新接口
        }
    }
}
