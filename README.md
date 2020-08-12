# `MVC`架构设计与三层模型

[TOC]

## 一、理论

### 1.1 `MVC`流程关系图

`VMC`流程关系如下图所示：

![image](https://github.com/tianyalu/NeMvcMode/raw/master/show/mvc_process_relation.png)

![image](https://github.com/tianyalu/NeMvcMode/raw/master/show/mvc_process_relation2.png)

### 1.2 三层模型

经典的三层模型：

![image](https://github.com/tianyalu/NeMvcMode/raw/master/show/typical_three_level_model.png)

三层模型在`Android`中的体现：

![image](https://github.com/tianyalu/NeMvcMode/raw/master/show/android_three_level_model.png)

## 二、实践

### 2.1 实现

本文使用`MVC`实现了点击按钮通过网络请求下载图片并渲染到视图上的操作，效果如下图所示：

![image](https://github.com/tianyalu/NeMvcMode/raw/master/show/show.gif)

其中`ImageBean`代表`Model`，`activity_main.xml`布局代表`View`，`MainActivity`代表`Controller`。上述整个过程就表达了从`View`--> `Controller` --> `Model` 然后又从 `Model` --> `Controller` --> `View` 的过程。

### 2.2 从`MVC`到`MVP`的演变

当用户回退`Activity`后，`Activity`本应该销毁，但如果其继续执行耗时任务导致不能销毁的话，最终会导致内存泄漏，由此引出`MVP`。

```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        //返回时导致Activity不能销毁 --> 内存泄漏
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(50000);
            }
        }).start();
    }
```

