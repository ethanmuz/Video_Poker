package com.ethanuzarowski.videopoker.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_settings{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("settingsback").vw.setLeft((int)((10d / 100 * width)));
views.get("settingsback").vw.setWidth((int)((35d / 100 * width) - ((10d / 100 * width))));
views.get("settingsback").vw.setTop((int)((5d / 100 * height)));
views.get("settingsback").vw.setHeight((int)((25d / 100 * height) - ((5d / 100 * height))));
views.get("settingsscroller").vw.setLeft((int)((0d / 100 * width)));
views.get("settingsscroller").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("settingsscroller").vw.setTop((int)((25d / 100 * height)));
views.get("settingsscroller").vw.setHeight((int)((100d / 100 * height) - ((25d / 100 * height))));

}
}