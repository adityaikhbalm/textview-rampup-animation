# TextView Ramp Up Animation


Custom TextView that show text in animation


## Download

Add it in your root build.gradle at the end of repositories :

```java
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}
```

Include the following dependency in your build.gradle file :

```java
dependencies {
    ...
    implementation 'com.github.adityaikhbalm:textview-rampup-animation:v1.0.0'
}
```

## Usage

```xml
    <com.adityaikhbalm.textviewrampupanimation.TextViewBackground
        android:id="@+id/textBackground"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textAlignment="center"
        android:paddingTop="100dp"/>
        
    <com.adityaikhbalm.textviewrampupanimation.TextViewAnimation
        android:id="@+id/textAnimate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textAlignment="center"
        android:paddingTop="100dp"/>
```        
        
**In main Activy or Fragment :**  

```java

val hello = "Hello World!"

textBackground.apply {
    animDuration = 100
    text = hello
}

textAnimate.apply {
    animDuration = 100
    text = hello
}
...
textBackground.startAnimation();
textAnimate.startAnimation();
```
       
### Attribute description

**Animation Duration :** size of characters during animation (default = 500)

## Examples

Don't forget set up top padding to get this effect

```xml
    <com.adityaikhbalm.textviewrampupanimation.TextViewBackground
        android:id="@+id/textBackground"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textAlignment="center"
        android:paddingTop="100dp"/>
        
    <com.adityaikhbalm.textviewrampupanimation.TextViewAnimation
        android:id="@+id/textAnimate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textAlignment="center"
        android:paddingTop="100dp"/>
```       

![picture alt](https://github.com/adityaikhbalm/textview-rampup-animation/blob/master/textviewanimation.gif)
