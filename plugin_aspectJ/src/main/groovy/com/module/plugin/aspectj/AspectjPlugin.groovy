package com.module.plugin.aspectj

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin

import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main;
import org.gradle.api.Plugin;
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile;

class AspectjPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        def hasApp = project.plugins.withType(AppPlugin)
        def hasLib = project.plugins.withType(LibraryPlugin)
        if (!hasApp && !hasLib) {
            throw new IllegalStateException("'android' or 'android-library' plugin required.")
        }

        final def log = project.logger
        final def variants
        if (hasApp) {
            variants = project.android.applicationVariants
        } else {
            variants = project.android.libraryVariants
        }

        // 哪个工程使用本插件时，就动态给哪个工程添加aspectj依赖。
        project.dependencies {
            implementation 'org.aspectj:aspectjrt:1.9.1'//aspectj 依赖
        }

        // 固定代码，直接粘贴来即可。
        variants.all { variant ->

            println("------------------ AspectJPlugin 执行编译1 ------------------")
            JavaCompile javaCompile = variant.javaCompile
            javaCompile.doLast {
                println("------------------ AspectJPlugin 执行编译2 ------------------")
                String[] args = ["-showWeaveInfo",
                                 "-1.8",
                                 "-inpath", javaCompile.destinationDir.toString(),
                                 "-aspectpath", javaCompile.classpath.asPath,
                                 "-d", javaCompile.destinationDir.toString(),
                                 "-classpath", javaCompile.classpath.asPath,
                                 "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)]
                log.debug "ajc args: " + Arrays.toString(args)

                MessageHandler handler = new MessageHandler(true)
                new Main().run(args, handler)
                for (IMessage message : handler.getMessages(null, true)) {
                    switch (message.getKind()) {
                        case IMessage.ABORT:
                        case IMessage.ERROR:
                        case IMessage.FAIL:
                            log.error message.message, message.thrown
                            break
                        case IMessage.WARNING:
                            log.warn message.message, message.thrown
                            break
                        case IMessage.INFO:
                            log.info message.message, message.thrown
                            break
                        case IMessage.DEBUG:
                            log.debug message.message, message.thrown
                            break
                    }
                }
            }
        }
    }
}

//"-showWeaveInfo",
//"-1.5",
//"-inpath", javaCompile.destinationDir.toString(),  //class的输出目录，作为aspectJ的输入，如 -inpath, D:\work\code\MaterializeYourApp\app\build\intermediates\classes\debug，切面定义文件可以在源文件里定义
//"-aspectpath", javaCompile.classpath.asPath,  //依赖的jar包，切面定义文件可以在包含在第三方依赖中
//"-d", javaCompile.destinationDir.toString(), //输出class的目录
//"-classpath", javaCompile.classpath.asPath,  //依赖的jar包
//"-bootclasspath", project.android.bootClasspath.join(File.pathSeparator) //-bootclasspath, C:\Users\yangwei-os\AppData\Local\Android\Sdk\platforms\android-25\android.jar
//
