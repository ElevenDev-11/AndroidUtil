
# 保留所有类名（类名不混淆）
-keepnames class cn.elevendev.utils.**

# 保留所有 public static 方法名不混淆
-keepclassmembers class cn.elevendev.utils.** {
    public <methods>;
}

# 保留源码和行号信息（便于调试）
-keepattributes SourceFile,LineNumberTable

# 禁用压缩，防止误删代码
-dontshrink