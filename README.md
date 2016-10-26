# andSkin 
支持原apk里面的资源切换和支持外部插件更换资源，但是现在只支持颜色和drawable，但是我感觉在设计的时候，方便扩展。当然对于在模式设计的问题可以一起探讨。
##运行本例子
1. 运行plugin
2. 生成的plugin.apk adb push 到 /data/data/com.rrtoyewx.andskin/cache/文件里面
3. 运行app
4. 自由切换皮肤吧，玩的愉快
##支持原apk里面的资源切换
遵照资源名称后面加相同的资源包的名称，详情请看例子中的
```
    //默认的资源名称
    <color name="bg_main">#FFFFFF</color>
    <color name="text_color_main">#000000</color>

    //blue主题的资源名称
    <!--blue-->
    <color name="bg_main_blue">#444444</color>
    <color name="text_color_main_blue">#1111AA</color>
    
    //red主题的资源名称
    <!--red-->
    <color name="bg_main_red">#AAAAAA</color>
    <color name="text_color_main_red">#AA2222</color>
```

同样的drawable也遵守这样的规则
```
img_main.jpg
img_main_blue.jpg
img_main_red.jpg
```

用法示例
```
        mChangeBlueSkinByLocalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinL.d("change skin local blue");
                SkinLoaderManager.getDefault().loadSkin("blue");
            }
        });

        mChangeRedSkinByLocalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinL.d("change skin local red");
                SkinLoaderManager.getDefault().loadSkin("red");
            }
        });
```

## 支持外部插件更换资源
同样遵守资源名称后面加相同的资源的名称，详情请看例子中
```
    //yellow的主题包的资源
    <!--yellow-->
    <color name="bg_main_yellow">#222222</color>
    <color name="text_color_main_yellow">#ffff00</color>
  
    //green的主题包的资源
    <!--green-->
    <color name="bg_main_green">#666666</color>
    <color name="text_color_main_green">#00aa00</color>
```
drawable资源
```
img_main_green.jpg
img_main_yellow.jpg
```

用法示例
```
      mChangYellowSkinPluginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinL.d("change skin plugin yellow");
                SkinLoaderManager.getDefault().loadSkin("com.rrtoyewx.plugin", "plugin-debug.apk", "yellow");
            }
        });

        mChangeGreenSkinPluginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinL.d("change skin plugin green");
                SkinLoaderManager.getDefault().loadSkin("com.rrtoyewx.plugin", "plugin-debug.apk", "green");
            }
        });
```

注意使用例子中的一定确保plugin-debug.apk 放到 /data/data/com.rrtoyewx.andskin/cache/文件目录里面，不然找初始化失败，另外这个目录可以更改，在
constant/ConfigConstants.java里面
```
    public static final String PATH_EXTERNAL_PLUGIN = GlobalManager.getDefault().getApplicationContext().getCacheDir().getAbsolutePath();

```
## 后续改进
1. deliver的引入，完善整个系统
2. 增加其他的属性
3. 兼容性
4. actionbar和状态栏
