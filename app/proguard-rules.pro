-dontskipnonpubliclibraryclasses # 不忽略非公共的库类
-optimizationpasses 5            # 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-dontusemixedcaseclassnames      # 是否使用大小写混合
# -dontpreverify                 # 混淆时是否做预校验 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-verbose                         # 混淆时是否记录日志 这句话能够使我们的项目混淆后产生映射文件
-keepattributes *Annotation*,InnerClasses # 保留Annotation不混淆
-ignorewarnings                 # 忽略警告
-dontoptimize                    # 优化不优化输入的类文件
-dontskipnonpubliclibraryclassmembers # 指定不去忽略非公共库的类成员

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 指定混淆是采用的算法，后面的参数是一个过滤器.这个过滤器是谷歌推荐的算法，一般不做更改

#保留我们使用的四大组件，自定义的Application等等这些类不被混淆,因为这些子类都有可能被外部调用
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
# 保留support下的所有类及其内部类
-keep class android.support.** {*;}


# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**
-keep public class * extends android.app.Fragment

# 保留R下面的资源
-keep class **.R$* {*;}

# 保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保留Parcelable序列化类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

# webView处理，项目中没有使用到webView忽略即可
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}

-keep class android.support.**{*;}

-keepclassmembers class ** {
    @com.yanzhenjie.permission.PermissionYes <methods>;
}
-keepclassmembers class ** {
    @com.yanzhenjie.permission.PermissionNo <methods>;
}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}


#okio
-dontwarn okio.**
#okhttp3
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature
# For using GSON @Expose annotation
-keepattributes *Annotation*
# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }
# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

-keep class com.strivexj.linkgame.bean.**{*;}

##---------------End: proguard configuration for Gson  ----------

##---------------Start: proguard configuration for Glide  ----------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
##---------------End: proguard configuration for Glide  ----------
##---------------Start: proguard configuration for bugly  ----------
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class android.support.**{*;}
##---------------End: proguard configuration for bugly  ----------


-dontwarn com.squareup.picasso.**



#------------umeng-------------------
-keep class com.umeng.** {*;}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep public class [您的应用包名].R$*{
public static final int *;
}