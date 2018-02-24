package com.ethanuzarowski.videopoker;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "com.ethanuzarowski.videopoker", "com.ethanuzarowski.videopoker.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "com.ethanuzarowski.videopoker", "com.ethanuzarowski.videopoker.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.ethanuzarowski.videopoker.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}

public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _settingsscroller = null;
public anywheresoftware.b4a.objects.HorizontalScrollViewWrapper _menuscroller = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnljacksorbetter = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlallamerican = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _menuimg = null;
public anywheresoftware.b4a.objects.PanelWrapper _menusettings = null;
public anywheresoftware.b4a.objects.PanelWrapper _menuinfo = null;
public anywheresoftware.b4a.objects.PanelWrapper _menucashier = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlscrollforgames = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlmoregamescomingsoon = null;
public anywheresoftware.b4a.objects.PanelWrapper _card1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _card2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _card3 = null;
public anywheresoftware.b4a.objects.PanelWrapper _card4 = null;
public anywheresoftware.b4a.objects.PanelWrapper _card5 = null;
public anywheresoftware.b4a.objects.PanelWrapper _paytable = null;
public anywheresoftware.b4a.objects.PanelWrapper _lblbet = null;
public anywheresoftware.b4a.objects.PanelWrapper _hold1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _hold2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _hold3 = null;
public anywheresoftware.b4a.objects.PanelWrapper _hold4 = null;
public anywheresoftware.b4a.objects.PanelWrapper _hold5 = null;
public anywheresoftware.b4a.objects.PanelWrapper _draw = null;
public anywheresoftware.b4a.objects.PanelWrapper _redraw = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblresult = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblbalance = null;
public anywheresoftware.b4a.objects.PanelWrapper _backbutton = null;
public anywheresoftware.b4a.objects.LabelWrapper _cashierbalance = null;
public anywheresoftware.b4a.objects.PanelWrapper _cashierback = null;
public anywheresoftware.b4a.objects.PanelWrapper _cashierreup = null;
public anywheresoftware.b4a.objects.PanelWrapper _infoback = null;
public anywheresoftware.b4a.objects.LabelWrapper _infolabel = null;
public anywheresoftware.b4a.objects.PanelWrapper _settingsback = null;
public anywheresoftware.b4a.objects.PanelWrapper _settingsreview = null;
public anywheresoftware.b4a.objects.PanelWrapper _settingsotherapps = null;
public anywheresoftware.b4a.objects.PanelWrapper _settingsfeedback = null;
public static String[] _vv3 = null;
public static int[] _vv4 = null;
public static String[] _vv5 = null;
public static String[] _vv6 = null;
public static String _vv7 = "";
public static String _vv0 = "";
public static int _vv1 = 0;
public static String _vvv1 = "";
public static String _vvv2 = "";
public static String _vvv3 = "";
public static int _v6 = 0;
public static int _v5 = 0;
public static String _vvv4 = "";
public static int _vvv5 = 0;
public com.ethanuzarowski.videopoker.starter _vvv6 = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
int _numberofgames = 0;
 //BA.debugLineNum = 101;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 103;BA.debugLine="Activity.LoadLayout(\"menu\")";
mostCurrent._activity.LoadLayout("menu",mostCurrent.activityBA);
 //BA.debugLineNum = 104;BA.debugLine="Dim numberOfGames As Int =2 '//NUMBER OF GAMES//";
_numberofgames = (int) (2);
 //BA.debugLineNum = 105;BA.debugLine="menuscroller.Panel.Width = menuscroller.Width * (";
mostCurrent._menuscroller.getPanel().setWidth((int) (mostCurrent._menuscroller.getWidth()*(_numberofgames+2)));
 //BA.debugLineNum = 106;BA.debugLine="pnlJacksOrBetter.RemoveView";
mostCurrent._pnljacksorbetter.RemoveView();
 //BA.debugLineNum = 107;BA.debugLine="pnlallamerican.Removeview";
mostCurrent._pnlallamerican.RemoveView();
 //BA.debugLineNum = 108;BA.debugLine="pnlscrollforgames.Removeview";
mostCurrent._pnlscrollforgames.RemoveView();
 //BA.debugLineNum = 109;BA.debugLine="pnlmoregamescomingsoon.RemoveView";
mostCurrent._pnlmoregamescomingsoon.RemoveView();
 //BA.debugLineNum = 110;BA.debugLine="menuscroller.Panel.AddView(pnlscrollforgames,((me";
mostCurrent._menuscroller.getPanel().AddView((android.view.View)(mostCurrent._pnlscrollforgames.getObject()),(int) (((mostCurrent._menuscroller.getWidth()/(double)5)+mostCurrent._menuscroller.getWidth()*0)),(int) ((mostCurrent._menuscroller.getHeight()/(double)10)),(int) ((mostCurrent._menuscroller.getWidth()*.6)),(int) ((mostCurrent._menuscroller.getHeight()*.8)));
 //BA.debugLineNum = 111;BA.debugLine="menuscroller.Panel.AddView(pnlallamerican,((menus";
mostCurrent._menuscroller.getPanel().AddView((android.view.View)(mostCurrent._pnlallamerican.getObject()),(int) (((mostCurrent._menuscroller.getWidth()/(double)5)+mostCurrent._menuscroller.getWidth()*2)),(int) ((mostCurrent._menuscroller.getHeight()/(double)10)),(int) ((mostCurrent._menuscroller.getWidth()*.6)),(int) ((mostCurrent._menuscroller.getHeight()*.8)));
 //BA.debugLineNum = 112;BA.debugLine="menuscroller.Panel.AddView(pnlJacksOrBetter,((men";
mostCurrent._menuscroller.getPanel().AddView((android.view.View)(mostCurrent._pnljacksorbetter.getObject()),(int) (((mostCurrent._menuscroller.getWidth()/(double)5)+mostCurrent._menuscroller.getWidth()*1)),(int) ((mostCurrent._menuscroller.getHeight()/(double)10)),(int) ((mostCurrent._menuscroller.getWidth()*.6)),(int) ((mostCurrent._menuscroller.getHeight()*.8)));
 //BA.debugLineNum = 113;BA.debugLine="menuscroller.Panel.AddView(pnlmoregamescomingsoon";
mostCurrent._menuscroller.getPanel().AddView((android.view.View)(mostCurrent._pnlmoregamescomingsoon.getObject()),(int) (((mostCurrent._menuscroller.getWidth()/(double)5)+mostCurrent._menuscroller.getWidth()*(_numberofgames+1))),(int) ((mostCurrent._menuscroller.getHeight()/(double)10)),(int) ((mostCurrent._menuscroller.getWidth()*.6)),(int) ((mostCurrent._menuscroller.getHeight()*.8)));
 //BA.debugLineNum = 114;BA.debugLine="betsize = 1";
_v5 = (int) (1);
 //BA.debugLineNum = 115;BA.debugLine="score = 1000";
_v6 = (int) (1000);
 //BA.debugLineNum = 116;BA.debugLine="ReadOptions";
_v7();
 //BA.debugLineNum = 118;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 123;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 124;BA.debugLine="SaveOptions";
_v0();
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 120;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 121;BA.debugLine="End Sub";
return "";
}
public static String  _backbutton_click() throws Exception{
 //BA.debugLineNum = 720;BA.debugLine="Sub backbutton_Click";
 //BA.debugLineNum = 721;BA.debugLine="If (currentdrawstatus<>1) Then";
if ((_vv1!=1)) { 
 //BA.debugLineNum = 722;BA.debugLine="card1.Visible = False";
mostCurrent._card1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 723;BA.debugLine="card2.Visible = False";
mostCurrent._card2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 724;BA.debugLine="card3.Visible = False";
mostCurrent._card3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 725;BA.debugLine="card4.Visible = False";
mostCurrent._card4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 726;BA.debugLine="card5.Visible = False";
mostCurrent._card5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 727;BA.debugLine="paytable.Visible = False";
mostCurrent._paytable.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 728;BA.debugLine="lblbet.Visible = False";
mostCurrent._lblbet.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 729;BA.debugLine="hold1.Visible = False";
mostCurrent._hold1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 730;BA.debugLine="hold2.Visible = False";
mostCurrent._hold2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 731;BA.debugLine="hold3.Visible = False";
mostCurrent._hold3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 732;BA.debugLine="hold4.Visible = False";
mostCurrent._hold4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 733;BA.debugLine="hold5.Visible = False";
mostCurrent._hold5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 734;BA.debugLine="Draw.Visible = False";
mostCurrent._draw.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 735;BA.debugLine="Redraw.Visible =False";
mostCurrent._redraw.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 736;BA.debugLine="lblresult.Visible = False";
mostCurrent._lblresult.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 737;BA.debugLine="lblbalance.Visible = False";
mostCurrent._lblbalance.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 738;BA.debugLine="backbutton.Visible = False";
mostCurrent._backbutton.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 739;BA.debugLine="Activity_Create(False)";
_activity_create(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 742;BA.debugLine="End Sub";
return "";
}
public static String  _card1_click() throws Exception{
 //BA.debugLineNum = 167;BA.debugLine="Sub card1_Click";
 //BA.debugLineNum = 168;BA.debugLine="If currentdrawstatus = 1 Then";
if (_vv1==1) { 
 //BA.debugLineNum = 169;BA.debugLine="If hold1.Visible = True Then";
if (mostCurrent._hold1.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 170;BA.debugLine="hold1.Visible = False";
mostCurrent._hold1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 172;BA.debugLine="hold1.Visible=True";
mostCurrent._hold1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 175;BA.debugLine="End Sub";
return "";
}
public static String  _card2_click() throws Exception{
 //BA.debugLineNum = 157;BA.debugLine="Sub card2_Click";
 //BA.debugLineNum = 158;BA.debugLine="If currentdrawstatus = 1 Then";
if (_vv1==1) { 
 //BA.debugLineNum = 159;BA.debugLine="If hold2.Visible = True Then";
if (mostCurrent._hold2.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 160;BA.debugLine="hold2.Visible = False";
mostCurrent._hold2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 162;BA.debugLine="hold2.Visible=True";
mostCurrent._hold2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 165;BA.debugLine="End Sub";
return "";
}
public static String  _card3_click() throws Exception{
 //BA.debugLineNum = 147;BA.debugLine="Sub card3_Click";
 //BA.debugLineNum = 148;BA.debugLine="If currentdrawstatus = 1 Then";
if (_vv1==1) { 
 //BA.debugLineNum = 149;BA.debugLine="If hold3.Visible = True Then";
if (mostCurrent._hold3.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 150;BA.debugLine="hold3.Visible = False";
mostCurrent._hold3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 152;BA.debugLine="hold3.Visible=True";
mostCurrent._hold3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 155;BA.debugLine="End Sub";
return "";
}
public static String  _card4_click() throws Exception{
 //BA.debugLineNum = 137;BA.debugLine="Sub card4_Click";
 //BA.debugLineNum = 138;BA.debugLine="If currentdrawstatus = 1 Then";
if (_vv1==1) { 
 //BA.debugLineNum = 139;BA.debugLine="If hold4.Visible = True Then";
if (mostCurrent._hold4.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 140;BA.debugLine="hold4.Visible = False";
mostCurrent._hold4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 142;BA.debugLine="hold4.Visible=True";
mostCurrent._hold4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 145;BA.debugLine="End Sub";
return "";
}
public static String  _card5_click() throws Exception{
 //BA.debugLineNum = 127;BA.debugLine="Sub card5_Click";
 //BA.debugLineNum = 128;BA.debugLine="If currentdrawstatus = 1 Then";
if (_vv1==1) { 
 //BA.debugLineNum = 129;BA.debugLine="If hold5.Visible = True Then";
if (mostCurrent._hold5.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 130;BA.debugLine="hold5.Visible = False";
mostCurrent._hold5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 132;BA.debugLine="hold5.Visible=True";
mostCurrent._hold5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 135;BA.debugLine="End Sub";
return "";
}
public static String  _cashierback_click() throws Exception{
 //BA.debugLineNum = 842;BA.debugLine="Sub cashierback_Click";
 //BA.debugLineNum = 843;BA.debugLine="Activity_Create(False)";
_activity_create(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 844;BA.debugLine="End Sub";
return "";
}
public static String  _cashierreup_click() throws Exception{
 //BA.debugLineNum = 845;BA.debugLine="Sub cashierreup_Click";
 //BA.debugLineNum = 846;BA.debugLine="If score < 1000 Then";
if (_v6<1000) { 
 //BA.debugLineNum = 847;BA.debugLine="score = 1000";
_v6 = (int) (1000);
 //BA.debugLineNum = 848;BA.debugLine="cashierbalance.Text = \"Balance: \" & score";
mostCurrent._cashierbalance.setText((Object)("Balance: "+BA.NumberToString(_v6)));
 //BA.debugLineNum = 849;BA.debugLine="Msgbox(\"Your balance has been increased to 1000\",";
anywheresoftware.b4a.keywords.Common.Msgbox("Your balance has been increased to 1000","Balance Refreshed",mostCurrent.activityBA);
 }else {
 //BA.debugLineNum = 851;BA.debugLine="Msgbox(\"Your balance must be below 1000 to refre";
anywheresoftware.b4a.keywords.Common.Msgbox("Your balance must be below 1000 to refresh","Balance Not Refreshed",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 853;BA.debugLine="SaveOptions";
_v0();
 //BA.debugLineNum = 854;BA.debugLine="End Sub";
return "";
}
public static int  _vv2(String _searchme,char _findme) throws Exception{
int _countme = 0;
int _x = 0;
 //BA.debugLineNum = 467;BA.debugLine="Public Sub CountChar(searchMe As String, findMe As";
 //BA.debugLineNum = 469;BA.debugLine="If Not(searchMe.Contains(findMe)) Then Return";
if (anywheresoftware.b4a.keywords.Common.Not(_searchme.contains(BA.ObjectToString(_findme)))) { 
if (true) return (int) (0);};
 //BA.debugLineNum = 471;BA.debugLine="Dim CountMe As Int = 0";
_countme = (int) (0);
 //BA.debugLineNum = 473;BA.debugLine="For x = 0 To searchMe.Length - 1";
{
final int step3 = 1;
final int limit3 = (int) (_searchme.length()-1);
for (_x = (int) (0) ; (step3 > 0 && _x <= limit3) || (step3 < 0 && _x >= limit3); _x = ((int)(0 + _x + step3)) ) {
 //BA.debugLineNum = 474;BA.debugLine="If searchMe.CharAt(x) = findMe Then";
if (_searchme.charAt(_x)==_findme) { 
 //BA.debugLineNum = 475;BA.debugLine="CountMe = CountMe + 1";
_countme = (int) (_countme+1);
 };
 }
};
 //BA.debugLineNum = 479;BA.debugLine="Return CountMe";
if (true) return _countme;
 //BA.debugLineNum = 481;BA.debugLine="End Sub";
return 0;
}
public static String  _draw_click() throws Exception{
int _i = 0;
 //BA.debugLineNum = 227;BA.debugLine="Sub Draw_Click";
 //BA.debugLineNum = 228;BA.debugLine="score = score - betsize";
_v6 = (int) (_v6-_v5);
 //BA.debugLineNum = 229;BA.debugLine="lblbalance.Text = \"Balance: \" & score";
mostCurrent._lblbalance.setText((Object)("Balance: "+BA.NumberToString(_v6)));
 //BA.debugLineNum = 230;BA.debugLine="lblresult.Text=\"\"";
mostCurrent._lblresult.setText((Object)(""));
 //BA.debugLineNum = 231;BA.debugLine="currentdrawstatus = 1";
_vv1 = (int) (1);
 //BA.debugLineNum = 232;BA.debugLine="For i = 0 To 5";
{
final int step5 = 1;
final int limit5 = (int) (5);
for (_i = (int) (0) ; (step5 > 0 && _i <= limit5) || (step5 < 0 && _i >= limit5); _i = ((int)(0 + _i + step5)) ) {
 //BA.debugLineNum = 233;BA.debugLine="card(i) = \"\"";
mostCurrent._vv3[_i] = "";
 }
};
 //BA.debugLineNum = 235;BA.debugLine="For i = 1 To 5";
{
final int step8 = 1;
final int limit8 = (int) (5);
for (_i = (int) (1) ; (step8 > 0 && _i <= limit8) || (step8 < 0 && _i >= limit8); _i = ((int)(0 + _i + step8)) ) {
 //BA.debugLineNum = 236;BA.debugLine="cardval(i) = Rnd(1,14)";
_vv4[_i] = anywheresoftware.b4a.keywords.Common.Rnd((int) (1),(int) (14));
 //BA.debugLineNum = 237;BA.debugLine="If cardval(i) =1 Then";
if (_vv4[_i]==1) { 
 //BA.debugLineNum = 238;BA.debugLine="cardchar(i) = \"a\"";
mostCurrent._vv5[_i] = "a";
 }else if(_vv4[_i]==10) { 
 //BA.debugLineNum = 240;BA.debugLine="cardchar(i) =\"t\"";
mostCurrent._vv5[_i] = "t";
 }else if(_vv4[_i]==11) { 
 //BA.debugLineNum = 242;BA.debugLine="cardchar(i) = \"j\"";
mostCurrent._vv5[_i] = "j";
 }else if(_vv4[_i]==12) { 
 //BA.debugLineNum = 244;BA.debugLine="cardchar(i) = \"q\"";
mostCurrent._vv5[_i] = "q";
 }else if(_vv4[_i]==13) { 
 //BA.debugLineNum = 246;BA.debugLine="cardchar(i) = \"k\"";
mostCurrent._vv5[_i] = "k";
 }else {
 //BA.debugLineNum = 248;BA.debugLine="cardchar(i) = \"\" & cardval(i) & \"\"";
mostCurrent._vv5[_i] = ""+BA.NumberToString(_vv4[_i])+"";
 };
 }
};
 //BA.debugLineNum = 251;BA.debugLine="For i = 1 To 5";
{
final int step24 = 1;
final int limit24 = (int) (5);
for (_i = (int) (1) ; (step24 > 0 && _i <= limit24) || (step24 < 0 && _i >= limit24); _i = ((int)(0 + _i + step24)) ) {
 //BA.debugLineNum = 252;BA.debugLine="cardsuit(i) = Rnd(1,5)";
mostCurrent._vv6[_i] = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Rnd((int) (1),(int) (5)));
 //BA.debugLineNum = 253;BA.debugLine="If cardsuit(i) = 1 Then";
if ((mostCurrent._vv6[_i]).equals(BA.NumberToString(1))) { 
 //BA.debugLineNum = 254;BA.debugLine="cardsuit(i) = \"c\"";
mostCurrent._vv6[_i] = "c";
 }else if((mostCurrent._vv6[_i]).equals(BA.NumberToString(2))) { 
 //BA.debugLineNum = 256;BA.debugLine="cardsuit(i) = \"d\"";
mostCurrent._vv6[_i] = "d";
 }else if((mostCurrent._vv6[_i]).equals(BA.NumberToString(3))) { 
 //BA.debugLineNum = 258;BA.debugLine="cardsuit(i) = \"s\"";
mostCurrent._vv6[_i] = "s";
 }else if((mostCurrent._vv6[_i]).equals(BA.NumberToString(4))) { 
 //BA.debugLineNum = 260;BA.debugLine="cardsuit(i) = \"h\"";
mostCurrent._vv6[_i] = "h";
 };
 }
};
 //BA.debugLineNum = 263;BA.debugLine="For i = 1 To 5";
{
final int step36 = 1;
final int limit36 = (int) (5);
for (_i = (int) (1) ; (step36 > 0 && _i <= limit36) || (step36 < 0 && _i >= limit36); _i = ((int)(0 + _i + step36)) ) {
 //BA.debugLineNum = 264;BA.debugLine="tempstring = cardchar(i) & cardsuit(i)";
mostCurrent._vv7 = mostCurrent._vv5[_i]+mostCurrent._vv6[_i];
 //BA.debugLineNum = 265;BA.debugLine="Do While ((tempstring = card(1)) Or (tempstring =";
while ((((mostCurrent._vv7).equals(mostCurrent._vv3[(int) (1)])) || ((mostCurrent._vv7).equals(mostCurrent._vv3[(int) (2)])) || ((mostCurrent._vv7).equals(mostCurrent._vv3[(int) (3)])) || ((mostCurrent._vv7).equals(mostCurrent._vv3[(int) (4)])) || ((mostCurrent._vv7).equals(mostCurrent._vv3[(int) (5)])))) {
 //BA.debugLineNum = 266;BA.debugLine="cardsuit(i) = Rnd(1,5)";
mostCurrent._vv6[_i] = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Rnd((int) (1),(int) (5)));
 //BA.debugLineNum = 267;BA.debugLine="If cardsuit(i) = 1 Then";
if ((mostCurrent._vv6[_i]).equals(BA.NumberToString(1))) { 
 //BA.debugLineNum = 268;BA.debugLine="cardsuit(i) = \"c\"";
mostCurrent._vv6[_i] = "c";
 }else if((mostCurrent._vv6[_i]).equals(BA.NumberToString(2))) { 
 //BA.debugLineNum = 270;BA.debugLine="cardsuit(i) = \"d\"";
mostCurrent._vv6[_i] = "d";
 }else if((mostCurrent._vv6[_i]).equals(BA.NumberToString(3))) { 
 //BA.debugLineNum = 272;BA.debugLine="cardsuit(i) = \"s\"";
mostCurrent._vv6[_i] = "s";
 }else if((mostCurrent._vv6[_i]).equals(BA.NumberToString(4))) { 
 //BA.debugLineNum = 274;BA.debugLine="cardsuit(i) = \"h\"";
mostCurrent._vv6[_i] = "h";
 };
 //BA.debugLineNum = 276;BA.debugLine="cardval(i) = Rnd(1,14)";
_vv4[_i] = anywheresoftware.b4a.keywords.Common.Rnd((int) (1),(int) (14));
 //BA.debugLineNum = 277;BA.debugLine="If cardval(i) =1 Then";
if (_vv4[_i]==1) { 
 //BA.debugLineNum = 278;BA.debugLine="cardchar(i) = \"a\"";
mostCurrent._vv5[_i] = "a";
 }else if(_vv4[_i]==10) { 
 //BA.debugLineNum = 280;BA.debugLine="cardchar(i) =\"t\"";
mostCurrent._vv5[_i] = "t";
 }else if(_vv4[_i]==11) { 
 //BA.debugLineNum = 282;BA.debugLine="cardchar(i) = \"j\"";
mostCurrent._vv5[_i] = "j";
 }else if(_vv4[_i]==12) { 
 //BA.debugLineNum = 284;BA.debugLine="cardchar(i) = \"q\"";
mostCurrent._vv5[_i] = "q";
 }else if(_vv4[_i]==13) { 
 //BA.debugLineNum = 286;BA.debugLine="cardchar(i) = \"k\"";
mostCurrent._vv5[_i] = "k";
 }else {
 //BA.debugLineNum = 288;BA.debugLine="cardchar(i) = \"\" & cardval(i) & \"\"";
mostCurrent._vv5[_i] = ""+BA.NumberToString(_vv4[_i])+"";
 };
 //BA.debugLineNum = 290;BA.debugLine="tempstring = cardchar(i) & cardsuit(i)";
mostCurrent._vv7 = mostCurrent._vv5[_i]+mostCurrent._vv6[_i];
 }
;
 //BA.debugLineNum = 304;BA.debugLine="card(i) = tempstring";
mostCurrent._vv3[_i] = mostCurrent._vv7;
 //BA.debugLineNum = 305;BA.debugLine="cardpicstring = tempstring & \".png\"";
mostCurrent._vv0 = mostCurrent._vv7+".png";
 //BA.debugLineNum = 306;BA.debugLine="If i = 1 Then";
if (_i==1) { 
 //BA.debugLineNum = 307;BA.debugLine="card1.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._card1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._vv0).getObject()));
 }else if(_i==2) { 
 //BA.debugLineNum = 309;BA.debugLine="card2.SetBackgroundImage(LoadBitmap(File.DirAsse";
mostCurrent._card2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._vv0).getObject()));
 }else if(_i==3) { 
 //BA.debugLineNum = 311;BA.debugLine="card3.SetBackgroundImage(LoadBitmap(File.DirAsse";
mostCurrent._card3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._vv0).getObject()));
 }else if(_i==4) { 
 //BA.debugLineNum = 313;BA.debugLine="card4.SetBackgroundImage(LoadBitmap(File.DirAsse";
mostCurrent._card4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._vv0).getObject()));
 }else if(_i==5) { 
 //BA.debugLineNum = 315;BA.debugLine="card5.SetBackgroundImage(LoadBitmap(File.DirAsse";
mostCurrent._card5.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._vv0).getObject()));
 };
 }
};
 //BA.debugLineNum = 318;BA.debugLine="Draw.Visible = False";
mostCurrent._draw.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 319;BA.debugLine="Redraw.Visible = True";
mostCurrent._redraw.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 320;BA.debugLine="SaveOptions";
_v0();
 //BA.debugLineNum = 321;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 34;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 39;BA.debugLine="Dim settingsscroller As ScrollView";
mostCurrent._settingsscroller = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim menuscroller As HorizontalScrollView";
mostCurrent._menuscroller = new anywheresoftware.b4a.objects.HorizontalScrollViewWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Dim pnlJacksOrBetter As Panel";
mostCurrent._pnljacksorbetter = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim pnlallamerican As Panel";
mostCurrent._pnlallamerican = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Dim menuimg As ImageView";
mostCurrent._menuimg = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Dim menusettings As Panel";
mostCurrent._menusettings = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Dim menuinfo As Panel";
mostCurrent._menuinfo = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Dim menucashier As Panel";
mostCurrent._menucashier = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Dim pnlscrollforgames As Panel";
mostCurrent._pnlscrollforgames = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Dim pnlmoregamescomingsoon As Panel";
mostCurrent._pnlmoregamescomingsoon = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Dim card1 As Panel";
mostCurrent._card1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Dim card2 As Panel";
mostCurrent._card2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Dim card3 As Panel";
mostCurrent._card3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Dim card4 As Panel";
mostCurrent._card4 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Dim card5 As Panel";
mostCurrent._card5 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Dim paytable As Panel";
mostCurrent._paytable = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Dim lblbet As Panel";
mostCurrent._lblbet = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Dim hold1 As Panel";
mostCurrent._hold1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Dim hold2 As Panel";
mostCurrent._hold2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Dim hold3 As Panel";
mostCurrent._hold3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Dim hold4 As Panel";
mostCurrent._hold4 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Dim hold5 As Panel";
mostCurrent._hold5 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Dim Draw As Panel";
mostCurrent._draw = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Dim Redraw As Panel";
mostCurrent._redraw = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 65;BA.debugLine="Dim lblresult As Label";
mostCurrent._lblresult = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Dim lblbalance As Label";
mostCurrent._lblbalance = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Dim backbutton As Panel";
mostCurrent._backbutton = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Dim cashierbalance As Label";
mostCurrent._cashierbalance = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Dim cashierback As Panel";
mostCurrent._cashierback = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 72;BA.debugLine="Dim cashierreup As Panel";
mostCurrent._cashierreup = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 75;BA.debugLine="Dim infoback As Panel";
mostCurrent._infoback = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 76;BA.debugLine="Dim infolabel As Label";
mostCurrent._infolabel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 79;BA.debugLine="Dim settingsback As Panel";
mostCurrent._settingsback = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 80;BA.debugLine="Dim settingsreview As Panel";
mostCurrent._settingsreview = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 81;BA.debugLine="Dim settingsotherapps As Panel";
mostCurrent._settingsotherapps = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 82;BA.debugLine="Dim settingsfeedback As Panel";
mostCurrent._settingsfeedback = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 85;BA.debugLine="Dim card(6) As String";
mostCurrent._vv3 = new String[(int) (6)];
java.util.Arrays.fill(mostCurrent._vv3,"");
 //BA.debugLineNum = 86;BA.debugLine="Dim cardval(6) As Int";
_vv4 = new int[(int) (6)];
;
 //BA.debugLineNum = 87;BA.debugLine="Dim cardchar(6) As String";
mostCurrent._vv5 = new String[(int) (6)];
java.util.Arrays.fill(mostCurrent._vv5,"");
 //BA.debugLineNum = 88;BA.debugLine="Dim cardsuit(6) As String";
mostCurrent._vv6 = new String[(int) (6)];
java.util.Arrays.fill(mostCurrent._vv6,"");
 //BA.debugLineNum = 89;BA.debugLine="Dim tempstring As String";
mostCurrent._vv7 = "";
 //BA.debugLineNum = 90;BA.debugLine="Dim cardpicstring As String";
mostCurrent._vv0 = "";
 //BA.debugLineNum = 91;BA.debugLine="Dim currentdrawstatus As Int";
_vv1 = 0;
 //BA.debugLineNum = 92;BA.debugLine="Dim tempbetsizestring As String";
mostCurrent._vvv1 = "";
 //BA.debugLineNum = 93;BA.debugLine="Dim handstring As String";
mostCurrent._vvv2 = "";
 //BA.debugLineNum = 94;BA.debugLine="Dim hand As String";
mostCurrent._vvv3 = "";
 //BA.debugLineNum = 95;BA.debugLine="Dim score As Int";
_v6 = 0;
 //BA.debugLineNum = 96;BA.debugLine="Dim betsize As Int";
_v5 = 0;
 //BA.debugLineNum = 97;BA.debugLine="Dim strgame As String";
mostCurrent._vvv4 = "";
 //BA.debugLineNum = 98;BA.debugLine="Dim cardsuit1 As Int";
_vvv5 = 0;
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
return "";
}
public static String  _hold1_click() throws Exception{
 //BA.debugLineNum = 217;BA.debugLine="Sub hold1_Click";
 //BA.debugLineNum = 218;BA.debugLine="If currentdrawstatus = 1 Then";
if (_vv1==1) { 
 //BA.debugLineNum = 219;BA.debugLine="If hold1.Visible = True Then";
if (mostCurrent._hold1.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 220;BA.debugLine="hold1.Visible = False";
mostCurrent._hold1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 222;BA.debugLine="hold1.Visible=True";
mostCurrent._hold1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 225;BA.debugLine="End Sub";
return "";
}
public static String  _hold2_click() throws Exception{
 //BA.debugLineNum = 207;BA.debugLine="Sub hold2_Click";
 //BA.debugLineNum = 208;BA.debugLine="If currentdrawstatus = 1 Then";
if (_vv1==1) { 
 //BA.debugLineNum = 209;BA.debugLine="If hold2.Visible = True Then";
if (mostCurrent._hold2.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 210;BA.debugLine="hold2.Visible = False";
mostCurrent._hold2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 212;BA.debugLine="hold2.Visible=True";
mostCurrent._hold2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 215;BA.debugLine="End Sub";
return "";
}
public static String  _hold3_click() throws Exception{
 //BA.debugLineNum = 197;BA.debugLine="Sub hold3_Click";
 //BA.debugLineNum = 198;BA.debugLine="If currentdrawstatus = 1 Then";
if (_vv1==1) { 
 //BA.debugLineNum = 199;BA.debugLine="If hold3.Visible = True Then";
if (mostCurrent._hold3.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 200;BA.debugLine="hold3.Visible = False";
mostCurrent._hold3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 202;BA.debugLine="hold3.Visible=True";
mostCurrent._hold3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 205;BA.debugLine="End Sub";
return "";
}
public static String  _hold4_click() throws Exception{
 //BA.debugLineNum = 187;BA.debugLine="Sub hold4_Click";
 //BA.debugLineNum = 188;BA.debugLine="If currentdrawstatus = 1 Then";
if (_vv1==1) { 
 //BA.debugLineNum = 189;BA.debugLine="If hold4.Visible = True Then";
if (mostCurrent._hold4.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 190;BA.debugLine="hold4.Visible = False";
mostCurrent._hold4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 192;BA.debugLine="hold4.Visible=True";
mostCurrent._hold4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 195;BA.debugLine="End Sub";
return "";
}
public static String  _hold5_click() throws Exception{
 //BA.debugLineNum = 177;BA.debugLine="Sub hold5_Click";
 //BA.debugLineNum = 178;BA.debugLine="If currentdrawstatus = 1 Then";
if (_vv1==1) { 
 //BA.debugLineNum = 179;BA.debugLine="If hold5.Visible = True Then";
if (mostCurrent._hold5.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 180;BA.debugLine="hold5.Visible = False";
mostCurrent._hold5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 182;BA.debugLine="hold5.Visible=True";
mostCurrent._hold5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 185;BA.debugLine="End Sub";
return "";
}
public static String  _infoback_click() throws Exception{
 //BA.debugLineNum = 814;BA.debugLine="Sub infoback_Click";
 //BA.debugLineNum = 815;BA.debugLine="infoback.Visible =False";
mostCurrent._infoback.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 816;BA.debugLine="infolabel.Visible = False";
mostCurrent._infolabel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 817;BA.debugLine="Activity_Create(False)";
_activity_create(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 818;BA.debugLine="End Sub";
return "";
}
public static char  _vvv7(int _i) throws Exception{
char _g = '\0';
 //BA.debugLineNum = 626;BA.debugLine="Public Sub intchar(i As Int) As Char";
 //BA.debugLineNum = 627;BA.debugLine="Dim g As Char";
_g = '\0';
 //BA.debugLineNum = 628;BA.debugLine="If i = 1 Then";
if (_i==1) { 
 //BA.debugLineNum = 629;BA.debugLine="Return \"a\"";
if (true) return BA.ObjectToChar("a");
 }else if(_i==10) { 
 //BA.debugLineNum = 631;BA.debugLine="Return \"t\"";
if (true) return BA.ObjectToChar("t");
 }else if(_i==11) { 
 //BA.debugLineNum = 633;BA.debugLine="Return \"j\"";
if (true) return BA.ObjectToChar("j");
 }else if(_i==12) { 
 //BA.debugLineNum = 635;BA.debugLine="Return \"q\"";
if (true) return BA.ObjectToChar("q");
 }else if(_i==13) { 
 //BA.debugLineNum = 637;BA.debugLine="Return \"k\"";
if (true) return BA.ObjectToChar("k");
 }else if(_i==14) { 
 //BA.debugLineNum = 639;BA.debugLine="Return \"a\"";
if (true) return BA.ObjectToChar("a");
 }else {
 //BA.debugLineNum = 641;BA.debugLine="g = i";
_g = BA.ObjectToChar(_i);
 //BA.debugLineNum = 642;BA.debugLine="Return g";
if (true) return _g;
 };
 //BA.debugLineNum = 644;BA.debugLine="End Sub";
return '\0';
}
public static int  _vvv0(String _hand1) throws Exception{
boolean _b = false;
int _i = 0;
 //BA.debugLineNum = 604;BA.debugLine="Public Sub isStraight(hand1 As String) As Int";
 //BA.debugLineNum = 605;BA.debugLine="Dim b As Boolean";
_b = false;
 //BA.debugLineNum = 606;BA.debugLine="b= False";
_b = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 607;BA.debugLine="For i = 1 To 10";
{
final int step3 = 1;
final int limit3 = (int) (10);
for (_i = (int) (1) ; (step3 > 0 && _i <= limit3) || (step3 < 0 && _i >= limit3); _i = ((int)(0 + _i + step3)) ) {
 //BA.debugLineNum = 608;BA.debugLine="If (((CountChar(hand1,(intchar(i)))) = 1) And ((C";
if ((((_vv2(_hand1,(_vvv7(_i))))==1) && ((_vv2(_hand1,(_vvv7((int) (_i+1)))))==1) && ((_vv2(_hand1,(_vvv7((int) (_i+2)))))==1) && ((_vv2(_hand1,(_vvv7((int) (_i+3)))))==1) && ((_vv2(_hand1,(_vvv7((int) (_i+4)))))==1))) { 
 //BA.debugLineNum = 609;BA.debugLine="If ((CountChar(hand1,\"a\") > 0) And (CountChar(ha";
if (((_vv2(_hand1,BA.ObjectToChar("a"))>0) && (_vv2(_hand1,BA.ObjectToChar("k"))>0))) { 
 //BA.debugLineNum = 610;BA.debugLine="b = True";
_b = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 611;BA.debugLine="Return 2";
if (true) return (int) (2);
 //BA.debugLineNum = 612;BA.debugLine="Exit";
if (true) break;
 }else {
 //BA.debugLineNum = 614;BA.debugLine="b= True";
_b = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 615;BA.debugLine="Return 1";
if (true) return (int) (1);
 //BA.debugLineNum = 616;BA.debugLine="Exit";
if (true) break;
 };
 };
 }
};
 //BA.debugLineNum = 620;BA.debugLine="If b = True Then";
if (_b==anywheresoftware.b4a.keywords.Common.True) { 
 }else {
 //BA.debugLineNum = 622;BA.debugLine="Return 0";
if (true) return (int) (0);
 };
 //BA.debugLineNum = 624;BA.debugLine="Return -1";
if (true) return (int) (-1);
 //BA.debugLineNum = 625;BA.debugLine="End Sub";
return 0;
}
public static String  _lblbet_click() throws Exception{
 //BA.debugLineNum = 452;BA.debugLine="Sub lblbet_Click";
 //BA.debugLineNum = 453;BA.debugLine="If currentdrawstatus <> 1 Then";
if (_vv1!=1) { 
 //BA.debugLineNum = 454;BA.debugLine="If betsize < 5 Then";
if (_v5<5) { 
 //BA.debugLineNum = 455;BA.debugLine="betsize = betsize + 1";
_v5 = (int) (_v5+1);
 }else if(_v5==5) { 
 //BA.debugLineNum = 457;BA.debugLine="betsize = 1";
_v5 = (int) (1);
 }else {
 //BA.debugLineNum = 459;BA.debugLine="Msgbox(\"Error in bet size, please close app and";
anywheresoftware.b4a.keywords.Common.Msgbox("Error in bet size, please close app and restart.","Error",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 461;BA.debugLine="tempbetsizestring = strgame & \"paytable\" & betsi";
mostCurrent._vvv1 = mostCurrent._vvv4+"paytable"+BA.NumberToString(_v5)+".png";
 //BA.debugLineNum = 462;BA.debugLine="paytable.SetBackgroundImage(LoadBitmap(File.DirAs";
mostCurrent._paytable.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._vvv1).getObject()));
 };
 //BA.debugLineNum = 464;BA.debugLine="lblbet.SetBackgroundImage(LoadBitmap(File.DirAsse";
mostCurrent._lblbet.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),("bs"+BA.NumberToString(_v5)+".png")).getObject()));
 //BA.debugLineNum = 465;BA.debugLine="End Sub";
return "";
}
public static String  _menucashier_click() throws Exception{
 //BA.debugLineNum = 803;BA.debugLine="Sub menucashier_Click";
 //BA.debugLineNum = 804;BA.debugLine="menucashier.Visible = False";
mostCurrent._menucashier.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 805;BA.debugLine="menuimg.Visible = False";
mostCurrent._menuimg.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 806;BA.debugLine="menuinfo.Visible = False";
mostCurrent._menuinfo.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 807;BA.debugLine="menuscroller.Visible = False";
mostCurrent._menuscroller.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 808;BA.debugLine="menusettings.Visible = False";
mostCurrent._menusettings.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 809;BA.debugLine="pnlallamerican.Visible = False";
mostCurrent._pnlallamerican.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 810;BA.debugLine="pnlJacksOrBetter.Visible = False";
mostCurrent._pnljacksorbetter.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 811;BA.debugLine="Activity.LoadLayout(\"cashier\")";
mostCurrent._activity.LoadLayout("cashier",mostCurrent.activityBA);
 //BA.debugLineNum = 812;BA.debugLine="cashierbalance.Text = \"Balance: \" & score";
mostCurrent._cashierbalance.setText((Object)("Balance: "+BA.NumberToString(_v6)));
 //BA.debugLineNum = 813;BA.debugLine="End Sub";
return "";
}
public static String  _menuinfo_click() throws Exception{
 //BA.debugLineNum = 792;BA.debugLine="Sub menuinfo_Click";
 //BA.debugLineNum = 793;BA.debugLine="menucashier.Visible = False";
mostCurrent._menucashier.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 794;BA.debugLine="menuimg.Visible = False";
mostCurrent._menuimg.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 795;BA.debugLine="menuinfo.Visible = False";
mostCurrent._menuinfo.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 796;BA.debugLine="menuscroller.Visible = False";
mostCurrent._menuscroller.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 797;BA.debugLine="menusettings.Visible = False";
mostCurrent._menusettings.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 798;BA.debugLine="pnlallamerican.Visible = False";
mostCurrent._pnlallamerican.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 799;BA.debugLine="pnlJacksOrBetter.Visible = False";
mostCurrent._pnljacksorbetter.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 800;BA.debugLine="Activity.LoadLayout(\"info\")";
mostCurrent._activity.LoadLayout("info",mostCurrent.activityBA);
 //BA.debugLineNum = 801;BA.debugLine="End Sub";
return "";
}
public static String  _menusettings_click() throws Exception{
int _settingsreviewtop = 0;
int _settingsfeedbacktop = 0;
int _settingsotherappstop = 0;
int _settingsreviewheight = 0;
int _settingsfeedbackheight = 0;
int _settingsotherappsheight = 0;
int _numberofsettingsbuttons = 0;
 //BA.debugLineNum = 760;BA.debugLine="Sub menusettings_Click";
 //BA.debugLineNum = 761;BA.debugLine="Dim settingsreviewtop As Int";
_settingsreviewtop = 0;
 //BA.debugLineNum = 762;BA.debugLine="Dim settingsfeedbacktop As Int";
_settingsfeedbacktop = 0;
 //BA.debugLineNum = 763;BA.debugLine="Dim settingsotherappstop As Int";
_settingsotherappstop = 0;
 //BA.debugLineNum = 764;BA.debugLine="Dim settingsreviewheight As Int";
_settingsreviewheight = 0;
 //BA.debugLineNum = 765;BA.debugLine="Dim settingsfeedbackheight As Int";
_settingsfeedbackheight = 0;
 //BA.debugLineNum = 766;BA.debugLine="Dim settingsotherappsheight As Int";
_settingsotherappsheight = 0;
 //BA.debugLineNum = 767;BA.debugLine="menucashier.Visible = False";
mostCurrent._menucashier.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 768;BA.debugLine="menuimg.Visible = False";
mostCurrent._menuimg.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 769;BA.debugLine="menuinfo.Visible = False";
mostCurrent._menuinfo.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 770;BA.debugLine="menuscroller.Visible = False";
mostCurrent._menuscroller.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 771;BA.debugLine="menusettings.Visible = False";
mostCurrent._menusettings.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 772;BA.debugLine="pnlallamerican.Visible = False";
mostCurrent._pnlallamerican.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 773;BA.debugLine="pnlJacksOrBetter.Visible = False";
mostCurrent._pnljacksorbetter.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 774;BA.debugLine="Activity.LoadLayout(\"settings\")";
mostCurrent._activity.LoadLayout("settings",mostCurrent.activityBA);
 //BA.debugLineNum = 775;BA.debugLine="Dim numberOfSettingsButtons As Int =3 '//NUMBER O";
_numberofsettingsbuttons = (int) (3);
 //BA.debugLineNum = 776;BA.debugLine="settingsscroller.Panel.Height = settingsscroller.";
mostCurrent._settingsscroller.getPanel().setHeight((int) (mostCurrent._settingsscroller.getHeight()*((1/(double)3)*_numberofsettingsbuttons)));
 //BA.debugLineNum = 777;BA.debugLine="settingsfeedback.RemoveView";
mostCurrent._settingsfeedback.RemoveView();
 //BA.debugLineNum = 778;BA.debugLine="settingsreview.RemoveView";
mostCurrent._settingsreview.RemoveView();
 //BA.debugLineNum = 779;BA.debugLine="settingsotherapps.RemoveView 'ltwh";
mostCurrent._settingsotherapps.RemoveView();
 //BA.debugLineNum = 781;BA.debugLine="settingsreviewtop=(.05*settingsscroller.Height)";
_settingsreviewtop = (int) ((.05*mostCurrent._settingsscroller.getHeight()));
 //BA.debugLineNum = 782;BA.debugLine="settingsreviewheight=(.25*settingsscroller.Height";
_settingsreviewheight = (int) ((.25*mostCurrent._settingsscroller.getHeight()));
 //BA.debugLineNum = 783;BA.debugLine="settingsotherappstop = settingsreviewtop+settings";
_settingsotherappstop = (int) (_settingsreviewtop+_settingsreviewheight+(.075*mostCurrent._settingsscroller.getHeight()));
 //BA.debugLineNum = 784;BA.debugLine="settingsotherappsheight = (.25*settingsscroller.H";
_settingsotherappsheight = (int) ((.25*mostCurrent._settingsscroller.getHeight()));
 //BA.debugLineNum = 785;BA.debugLine="settingsfeedbacktop = settingsotherappstop+settin";
_settingsfeedbacktop = (int) (_settingsotherappstop+_settingsotherappsheight+(.075*mostCurrent._settingsscroller.getHeight()));
 //BA.debugLineNum = 786;BA.debugLine="settingsfeedbackheight =(.25*settingsscroller.Hei";
_settingsfeedbackheight = (int) ((.25*mostCurrent._settingsscroller.getHeight()));
 //BA.debugLineNum = 787;BA.debugLine="settingsscroller.Panel.AddView(settingsreview, (.";
mostCurrent._settingsscroller.getPanel().AddView((android.view.View)(mostCurrent._settingsreview.getObject()),(int) ((.1*mostCurrent._settingsscroller.getWidth())),_settingsreviewtop,(int) ((.8*mostCurrent._settingsscroller.getWidth())),_settingsreviewheight);
 //BA.debugLineNum = 788;BA.debugLine="settingsscroller.Panel.AddView(settingsotherapps,";
mostCurrent._settingsscroller.getPanel().AddView((android.view.View)(mostCurrent._settingsotherapps.getObject()),(int) ((.1*mostCurrent._settingsscroller.getWidth())),_settingsotherappstop,(int) ((.8*mostCurrent._settingsscroller.getWidth())),_settingsotherappsheight);
 //BA.debugLineNum = 789;BA.debugLine="settingsscroller.Panel.AddView(settingsfeedback,";
mostCurrent._settingsscroller.getPanel().AddView((android.view.View)(mostCurrent._settingsfeedback.getObject()),(int) ((.1*mostCurrent._settingsscroller.getWidth())),_settingsfeedbacktop,(int) ((.8*mostCurrent._settingsscroller.getWidth())),_settingsfeedbackheight);
 //BA.debugLineNum = 790;BA.debugLine="End Sub";
return "";
}
public static String  _paytable_click() throws Exception{
 //BA.debugLineNum = 437;BA.debugLine="Sub paytable_Click";
 //BA.debugLineNum = 438;BA.debugLine="If currentdrawstatus <> 1 Then";
if (_vv1!=1) { 
 //BA.debugLineNum = 439;BA.debugLine="If betsize < 5 Then";
if (_v5<5) { 
 //BA.debugLineNum = 440;BA.debugLine="betsize = betsize + 1";
_v5 = (int) (_v5+1);
 }else if(_v5==5) { 
 //BA.debugLineNum = 442;BA.debugLine="betsize = 1";
_v5 = (int) (1);
 }else {
 //BA.debugLineNum = 444;BA.debugLine="Msgbox(\"Error in bet size, please close app and";
anywheresoftware.b4a.keywords.Common.Msgbox("Error in bet size, please close app and restart.","Error",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 446;BA.debugLine="tempbetsizestring = strgame & \"paytable\" & betsi";
mostCurrent._vvv1 = mostCurrent._vvv4+"paytable"+BA.NumberToString(_v5)+".png";
 //BA.debugLineNum = 447;BA.debugLine="paytable.SetBackgroundImage(LoadBitmap(File.DirAs";
mostCurrent._paytable.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._vvv1).getObject()));
 };
 //BA.debugLineNum = 449;BA.debugLine="lblbet.SetBackgroundImage(LoadBitmap(File.DirAsse";
mostCurrent._lblbet.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),("bs"+BA.NumberToString(_v5)+".png")).getObject()));
 //BA.debugLineNum = 450;BA.debugLine="End Sub";
return "";
}
public static String  _pnlallamerican_click() throws Exception{
 //BA.debugLineNum = 743;BA.debugLine="Sub pnlAllAmerican_Click";
 //BA.debugLineNum = 744;BA.debugLine="strgame = \"allamerican\"";
mostCurrent._vvv4 = "allamerican";
 //BA.debugLineNum = 745;BA.debugLine="menucashier.Visible = False";
mostCurrent._menucashier.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 746;BA.debugLine="menuimg.Visible = False";
mostCurrent._menuimg.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 747;BA.debugLine="menuinfo.Visible = False";
mostCurrent._menuinfo.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 748;BA.debugLine="menuscroller.Visible = False";
mostCurrent._menuscroller.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 749;BA.debugLine="menusettings.Visible = False";
mostCurrent._menusettings.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 750;BA.debugLine="pnlallamerican.Visible = False";
mostCurrent._pnlallamerican.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 751;BA.debugLine="pnlJacksOrBetter.Visible = False";
mostCurrent._pnljacksorbetter.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 752;BA.debugLine="Activity.LoadLayout(\"game\")";
mostCurrent._activity.LoadLayout("game",mostCurrent.activityBA);
 //BA.debugLineNum = 754;BA.debugLine="tempbetsizestring = strgame & \"paytable\" & betsiz";
mostCurrent._vvv1 = mostCurrent._vvv4+"paytable"+BA.NumberToString(_v5)+".png";
 //BA.debugLineNum = 755;BA.debugLine="paytable.SetBackgroundImage(LoadBitmap(File.DirAs";
mostCurrent._paytable.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._vvv1).getObject()));
 //BA.debugLineNum = 756;BA.debugLine="paytable.Visible = True";
mostCurrent._paytable.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 757;BA.debugLine="lblbalance.Text = \"Balance: \" & score";
mostCurrent._lblbalance.setText((Object)("Balance: "+BA.NumberToString(_v6)));
 //BA.debugLineNum = 758;BA.debugLine="End Sub";
return "";
}
public static String  _pnljacksorbetter_click() throws Exception{
 //BA.debugLineNum = 702;BA.debugLine="Sub pnlJacksOrBetter_Click";
 //BA.debugLineNum = 703;BA.debugLine="strgame = \"jacksorbetter\"";
mostCurrent._vvv4 = "jacksorbetter";
 //BA.debugLineNum = 704;BA.debugLine="menucashier.Visible = False";
mostCurrent._menucashier.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 705;BA.debugLine="menuimg.Visible = False";
mostCurrent._menuimg.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 706;BA.debugLine="menuinfo.Visible = False";
mostCurrent._menuinfo.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 707;BA.debugLine="menuscroller.Visible = False";
mostCurrent._menuscroller.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 708;BA.debugLine="menusettings.Visible = False";
mostCurrent._menusettings.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 709;BA.debugLine="pnlallamerican.Visible = False";
mostCurrent._pnlallamerican.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 710;BA.debugLine="pnlJacksOrBetter.Visible = False";
mostCurrent._pnljacksorbetter.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 711;BA.debugLine="Activity.LoadLayout(\"game\")";
mostCurrent._activity.LoadLayout("game",mostCurrent.activityBA);
 //BA.debugLineNum = 713;BA.debugLine="tempbetsizestring = strgame & \"paytable\" & betsiz";
mostCurrent._vvv1 = mostCurrent._vvv4+"paytable"+BA.NumberToString(_v5)+".png";
 //BA.debugLineNum = 714;BA.debugLine="paytable.SetBackgroundImage(LoadBitmap(File.DirAs";
mostCurrent._paytable.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._vvv1).getObject()));
 //BA.debugLineNum = 715;BA.debugLine="paytable.Visible = True";
mostCurrent._paytable.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 716;BA.debugLine="lblbalance.Text = \"Balance: \" & score";
mostCurrent._lblbalance.setText((Object)("Balance: "+BA.NumberToString(_v6)));
 //BA.debugLineNum = 718;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
starter._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public static String  _v7() throws Exception{
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _tw = null;
 //BA.debugLineNum = 27;BA.debugLine="Sub ReadOptions";
 //BA.debugLineNum = 28;BA.debugLine="If File.Exists(File.DirInternal, \"[file name]\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"[file name]")==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 29;BA.debugLine="Dim TW As TextReader";
_tw = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();
 //BA.debugLineNum = 30;BA.debugLine="TW.Initialize(File.OpenInput(File.DirInternal,";
_tw.Initialize((java.io.InputStream)(anywheresoftware.b4a.keywords.Common.File.OpenInput(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"[file name]").getObject()));
 //BA.debugLineNum = 31;BA.debugLine="score = TW.ReadLine";
_v6 = (int)(Double.parseDouble(_tw.ReadLine()));
 //BA.debugLineNum = 32;BA.debugLine="TW.Close";
_tw.Close();
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}
public static String  _redraw_click() throws Exception{
int _i = 0;
 //BA.debugLineNum = 323;BA.debugLine="Sub Redraw_Click";
 //BA.debugLineNum = 324;BA.debugLine="currentdrawstatus = 2";
_vv1 = (int) (2);
 //BA.debugLineNum = 325;BA.debugLine="For i = 0 To 5";
{
final int step2 = 1;
final int limit2 = (int) (5);
for (_i = (int) (0) ; (step2 > 0 && _i <= limit2) || (step2 < 0 && _i >= limit2); _i = ((int)(0 + _i + step2)) ) {
 //BA.debugLineNum = 326;BA.debugLine="If (((i=1) And (hold1.Visible = False)) Or ((i=2";
if ((((_i==1) && (mostCurrent._hold1.getVisible()==anywheresoftware.b4a.keywords.Common.False)) || ((_i==2) && (mostCurrent._hold2.getVisible()==anywheresoftware.b4a.keywords.Common.False)) || ((_i==3) && (mostCurrent._hold3.getVisible()==anywheresoftware.b4a.keywords.Common.False)) || ((_i==4) && (mostCurrent._hold4.getVisible()==anywheresoftware.b4a.keywords.Common.False)) || ((_i==5) && (mostCurrent._hold5.getVisible()==anywheresoftware.b4a.keywords.Common.False)))) { 
 //BA.debugLineNum = 327;BA.debugLine="card(i) = \"\"";
mostCurrent._vv3[_i] = "";
 };
 }
};
 //BA.debugLineNum = 330;BA.debugLine="For i = 1 To 5";
{
final int step7 = 1;
final int limit7 = (int) (5);
for (_i = (int) (1) ; (step7 > 0 && _i <= limit7) || (step7 < 0 && _i >= limit7); _i = ((int)(0 + _i + step7)) ) {
 //BA.debugLineNum = 331;BA.debugLine="If (((i=1) And (hold1.Visible = False)) Or ((i=2";
if ((((_i==1) && (mostCurrent._hold1.getVisible()==anywheresoftware.b4a.keywords.Common.False)) || ((_i==2) && (mostCurrent._hold2.getVisible()==anywheresoftware.b4a.keywords.Common.False)) || ((_i==3) && (mostCurrent._hold3.getVisible()==anywheresoftware.b4a.keywords.Common.False)) || ((_i==4) && (mostCurrent._hold4.getVisible()==anywheresoftware.b4a.keywords.Common.False)) || ((_i==5) && (mostCurrent._hold5.getVisible()==anywheresoftware.b4a.keywords.Common.False)))) { 
 //BA.debugLineNum = 332;BA.debugLine="cardval(i) = Rnd(1,14)";
_vv4[_i] = anywheresoftware.b4a.keywords.Common.Rnd((int) (1),(int) (14));
 //BA.debugLineNum = 333;BA.debugLine="If cardval(i) =1 Then";
if (_vv4[_i]==1) { 
 //BA.debugLineNum = 334;BA.debugLine="cardchar(i) = \"a\"";
mostCurrent._vv5[_i] = "a";
 }else if(_vv4[_i]==10) { 
 //BA.debugLineNum = 336;BA.debugLine="cardchar(i) =\"t\"";
mostCurrent._vv5[_i] = "t";
 }else if(_vv4[_i]==11) { 
 //BA.debugLineNum = 338;BA.debugLine="cardchar(i) = \"j\"";
mostCurrent._vv5[_i] = "j";
 }else if(_vv4[_i]==12) { 
 //BA.debugLineNum = 340;BA.debugLine="cardchar(i) = \"q\"";
mostCurrent._vv5[_i] = "q";
 }else if(_vv4[_i]==13) { 
 //BA.debugLineNum = 342;BA.debugLine="cardchar(i) = \"k\"";
mostCurrent._vv5[_i] = "k";
 }else {
 //BA.debugLineNum = 344;BA.debugLine="cardchar(i) = \"\" & cardval(i) & \"\"";
mostCurrent._vv5[_i] = ""+BA.NumberToString(_vv4[_i])+"";
 };
 };
 }
};
 //BA.debugLineNum = 348;BA.debugLine="For i = 1 To 5";
{
final int step25 = 1;
final int limit25 = (int) (5);
for (_i = (int) (1) ; (step25 > 0 && _i <= limit25) || (step25 < 0 && _i >= limit25); _i = ((int)(0 + _i + step25)) ) {
 //BA.debugLineNum = 349;BA.debugLine="If (((i=1) And (hold1.Visible = False)) Or ((i=2";
if ((((_i==1) && (mostCurrent._hold1.getVisible()==anywheresoftware.b4a.keywords.Common.False)) || ((_i==2) && (mostCurrent._hold2.getVisible()==anywheresoftware.b4a.keywords.Common.False)) || ((_i==3) && (mostCurrent._hold3.getVisible()==anywheresoftware.b4a.keywords.Common.False)) || ((_i==4) && (mostCurrent._hold4.getVisible()==anywheresoftware.b4a.keywords.Common.False)) || ((_i==5) && (mostCurrent._hold5.getVisible()==anywheresoftware.b4a.keywords.Common.False)))) { 
 //BA.debugLineNum = 350;BA.debugLine="cardsuit1 = Rnd(1,5)";
_vvv5 = anywheresoftware.b4a.keywords.Common.Rnd((int) (1),(int) (5));
 //BA.debugLineNum = 351;BA.debugLine="If cardsuit1 = 1 Then";
if (_vvv5==1) { 
 //BA.debugLineNum = 352;BA.debugLine="cardsuit(i) = \"c\"";
mostCurrent._vv6[_i] = "c";
 }else if(_vvv5==2) { 
 //BA.debugLineNum = 354;BA.debugLine="cardsuit(i) = \"d\"";
mostCurrent._vv6[_i] = "d";
 }else if(_vvv5==3) { 
 //BA.debugLineNum = 356;BA.debugLine="cardsuit(i) = \"s\"";
mostCurrent._vv6[_i] = "s";
 }else if(_vvv5==4) { 
 //BA.debugLineNum = 358;BA.debugLine="cardsuit(i) = \"h\"";
mostCurrent._vv6[_i] = "h";
 };
 };
 }
};
 //BA.debugLineNum = 362;BA.debugLine="For i = 1 To 5";
{
final int step39 = 1;
final int limit39 = (int) (5);
for (_i = (int) (1) ; (step39 > 0 && _i <= limit39) || (step39 < 0 && _i >= limit39); _i = ((int)(0 + _i + step39)) ) {
 //BA.debugLineNum = 363;BA.debugLine="If (((i=1) And (hold1.Visible = False)) Or ((i=2";
if ((((_i==1) && (mostCurrent._hold1.getVisible()==anywheresoftware.b4a.keywords.Common.False)) || ((_i==2) && (mostCurrent._hold2.getVisible()==anywheresoftware.b4a.keywords.Common.False)) || ((_i==3) && (mostCurrent._hold3.getVisible()==anywheresoftware.b4a.keywords.Common.False)) || ((_i==4) && (mostCurrent._hold4.getVisible()==anywheresoftware.b4a.keywords.Common.False)) || ((_i==5) && (mostCurrent._hold5.getVisible()==anywheresoftware.b4a.keywords.Common.False)))) { 
 //BA.debugLineNum = 364;BA.debugLine="tempstring = cardchar(i) & cardsuit(i)";
mostCurrent._vv7 = mostCurrent._vv5[_i]+mostCurrent._vv6[_i];
 //BA.debugLineNum = 365;BA.debugLine="Do While ((tempstring = card(1)) Or (tempstring =";
while ((((mostCurrent._vv7).equals(mostCurrent._vv3[(int) (1)])) || ((mostCurrent._vv7).equals(mostCurrent._vv3[(int) (2)])) || ((mostCurrent._vv7).equals(mostCurrent._vv3[(int) (3)])) || ((mostCurrent._vv7).equals(mostCurrent._vv3[(int) (4)])) || ((mostCurrent._vv7).equals(mostCurrent._vv3[(int) (5)])))) {
 //BA.debugLineNum = 366;BA.debugLine="cardsuit1 = Rnd(1,5)";
_vvv5 = anywheresoftware.b4a.keywords.Common.Rnd((int) (1),(int) (5));
 //BA.debugLineNum = 367;BA.debugLine="If cardsuit1 = 1 Then";
if (_vvv5==1) { 
 //BA.debugLineNum = 368;BA.debugLine="cardsuit(i) = \"c\"";
mostCurrent._vv6[_i] = "c";
 }else if(_vvv5==2) { 
 //BA.debugLineNum = 370;BA.debugLine="cardsuit(i) = \"d\"";
mostCurrent._vv6[_i] = "d";
 }else if(_vvv5==3) { 
 //BA.debugLineNum = 372;BA.debugLine="cardsuit(i) = \"s\"";
mostCurrent._vv6[_i] = "s";
 }else if(_vvv5==4) { 
 //BA.debugLineNum = 374;BA.debugLine="cardsuit(i) = \"h\"";
mostCurrent._vv6[_i] = "h";
 };
 //BA.debugLineNum = 376;BA.debugLine="cardval(i) = Rnd(1,14)";
_vv4[_i] = anywheresoftware.b4a.keywords.Common.Rnd((int) (1),(int) (14));
 //BA.debugLineNum = 377;BA.debugLine="If cardval(i) =1 Then";
if (_vv4[_i]==1) { 
 //BA.debugLineNum = 378;BA.debugLine="cardchar(i) = \"a\"";
mostCurrent._vv5[_i] = "a";
 }else if(_vv4[_i]==10) { 
 //BA.debugLineNum = 380;BA.debugLine="cardchar(i) =\"t\"";
mostCurrent._vv5[_i] = "t";
 }else if(_vv4[_i]==11) { 
 //BA.debugLineNum = 382;BA.debugLine="cardchar(i) = \"j\"";
mostCurrent._vv5[_i] = "j";
 }else if(_vv4[_i]==12) { 
 //BA.debugLineNum = 384;BA.debugLine="cardchar(i) = \"q\"";
mostCurrent._vv5[_i] = "q";
 }else if(_vv4[_i]==13) { 
 //BA.debugLineNum = 386;BA.debugLine="cardchar(i) = \"k\"";
mostCurrent._vv5[_i] = "k";
 }else {
 //BA.debugLineNum = 388;BA.debugLine="cardchar(i) = \"\" & cardval(i) & \"\"";
mostCurrent._vv5[_i] = ""+BA.NumberToString(_vv4[_i])+"";
 };
 //BA.debugLineNum = 390;BA.debugLine="tempstring = cardchar(i) & cardsuit(i)";
mostCurrent._vv7 = mostCurrent._vv5[_i]+mostCurrent._vv6[_i];
 }
;
 //BA.debugLineNum = 392;BA.debugLine="card(i) = tempstring";
mostCurrent._vv3[_i] = mostCurrent._vv7;
 //BA.debugLineNum = 393;BA.debugLine="cardpicstring = tempstring & \".png\"";
mostCurrent._vv0 = mostCurrent._vv7+".png";
 //BA.debugLineNum = 394;BA.debugLine="If i = 1 Then";
if (_i==1) { 
 //BA.debugLineNum = 395;BA.debugLine="card1.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._card1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._vv0).getObject()));
 }else if(_i==2) { 
 //BA.debugLineNum = 397;BA.debugLine="card2.SetBackgroundImage(LoadBitmap(File.DirAsse";
mostCurrent._card2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._vv0).getObject()));
 }else if(_i==3) { 
 //BA.debugLineNum = 399;BA.debugLine="card3.SetBackgroundImage(LoadBitmap(File.DirAsse";
mostCurrent._card3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._vv0).getObject()));
 }else if(_i==4) { 
 //BA.debugLineNum = 401;BA.debugLine="card4.SetBackgroundImage(LoadBitmap(File.DirAsse";
mostCurrent._card4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._vv0).getObject()));
 }else if(_i==5) { 
 //BA.debugLineNum = 403;BA.debugLine="card5.SetBackgroundImage(LoadBitmap(File.DirAsse";
mostCurrent._card5.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._vv0).getObject()));
 };
 };
 }
};
 //BA.debugLineNum = 407;BA.debugLine="hold1.Visible = False";
mostCurrent._hold1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 408;BA.debugLine="hold2.Visible = False";
mostCurrent._hold2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 409;BA.debugLine="hold3.Visible = False";
mostCurrent._hold3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 410;BA.debugLine="hold4.Visible = False";
mostCurrent._hold4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 411;BA.debugLine="hold5.Visible = False";
mostCurrent._hold5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 412;BA.debugLine="Redraw.Visible = False";
mostCurrent._redraw.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 413;BA.debugLine="Draw.Visible = True";
mostCurrent._draw.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 414;BA.debugLine="handstring = card(1) & \" \" & card(2) & \" \" & card";
mostCurrent._vvv2 = mostCurrent._vv3[(int) (1)]+" "+mostCurrent._vv3[(int) (2)]+" "+mostCurrent._vv3[(int) (3)]+" "+mostCurrent._vv3[(int) (4)]+" "+mostCurrent._vv3[(int) (5)];
 //BA.debugLineNum = 415;BA.debugLine="hand = returnhand1(strgame,handstring)";
mostCurrent._vvv3 = _vvvv1(mostCurrent._vvv4,mostCurrent._vvv2);
 //BA.debugLineNum = 416;BA.debugLine="lblresult.Text = hand & \" +\"&returnPoints1(hand,b";
mostCurrent._lblresult.setText((Object)(mostCurrent._vvv3+" +"+BA.NumberToString(_vvvv2(mostCurrent._vvv3,_v5,mostCurrent._vvv4))));
 //BA.debugLineNum = 417;BA.debugLine="score = score + returnPoints1(hand,betsize,strgam";
_v6 = (int) (_v6+_vvvv2(mostCurrent._vvv3,_v5,mostCurrent._vvv4));
 //BA.debugLineNum = 418;BA.debugLine="lblbalance.Text = \"Balance: \" & score";
mostCurrent._lblbalance.setText((Object)("Balance: "+BA.NumberToString(_v6)));
 //BA.debugLineNum = 419;BA.debugLine="SaveOptions";
_v0();
 //BA.debugLineNum = 420;BA.debugLine="End Sub";
return "";
}
public static String  _vvvv1(String _a,String _b) throws Exception{
 //BA.debugLineNum = 421;BA.debugLine="Sub returnhand1(a As String, b As String) As Strin";
 //BA.debugLineNum = 422;BA.debugLine="If (a=\"jacksorbetter\") Then";
if (((_a).equals("jacksorbetter"))) { 
 //BA.debugLineNum = 423;BA.debugLine="Return returnHandJacksOrBetter(b)";
if (true) return _vvvv3(_b);
 }else if(((_a).equals("allamerican"))) { 
 //BA.debugLineNum = 425;BA.debugLine="Return returnHandAllAmerican(b)";
if (true) return _vvvv4(_b);
 };
 //BA.debugLineNum = 427;BA.debugLine="Return \"\"";
if (true) return "";
 //BA.debugLineNum = 428;BA.debugLine="End Sub";
return "";
}
public static String  _vvvv4(String _hand1) throws Exception{
boolean _flush = false;
int _straight = 0;
boolean _fourofakind = false;
boolean _threeofakind = false;
int _pairs = 0;
char _ii = '\0';
int _i = 0;
 //BA.debugLineNum = 543;BA.debugLine="Public Sub returnHandAllAmerican(hand1 As String)";
 //BA.debugLineNum = 544;BA.debugLine="Dim flush As Boolean";
_flush = false;
 //BA.debugLineNum = 545;BA.debugLine="Dim straight As Int";
_straight = 0;
 //BA.debugLineNum = 546;BA.debugLine="Dim fourofakind As Boolean";
_fourofakind = false;
 //BA.debugLineNum = 547;BA.debugLine="Dim threeofakind As Boolean";
_threeofakind = false;
 //BA.debugLineNum = 548;BA.debugLine="Dim pairs As Int";
_pairs = 0;
 //BA.debugLineNum = 549;BA.debugLine="pairs = 0";
_pairs = (int) (0);
 //BA.debugLineNum = 550;BA.debugLine="straight = 0";
_straight = (int) (0);
 //BA.debugLineNum = 551;BA.debugLine="Dim ii As Char";
_ii = '\0';
 //BA.debugLineNum = 552;BA.debugLine="If ((CountChar(hand1,\"c\")) = 5 Or (CountChar(hand";
if (((_vv2(_hand1,BA.ObjectToChar("c")))==5 || (_vv2(_hand1,BA.ObjectToChar("h")))==5 || (_vv2(_hand1,BA.ObjectToChar("d")))==5 || (_vv2(_hand1,BA.ObjectToChar("s")))==5)) { 
 //BA.debugLineNum = 553;BA.debugLine="flush = True";
_flush = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 555;BA.debugLine="straight = isStraight(hand1)";
_straight = _vvv0(_hand1);
 //BA.debugLineNum = 556;BA.debugLine="If straight = -1 Then";
if (_straight==-1) { 
 //BA.debugLineNum = 557;BA.debugLine="Msgbox(\"Unknown error, please close app and rest";
anywheresoftware.b4a.keywords.Common.Msgbox("Unknown error, please close app and restart.","Error",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 559;BA.debugLine="For i = 1 To 13";
{
final int step16 = 1;
final int limit16 = (int) (13);
for (_i = (int) (1) ; (step16 > 0 && _i <= limit16) || (step16 < 0 && _i >= limit16); _i = ((int)(0 + _i + step16)) ) {
 //BA.debugLineNum = 560;BA.debugLine="If i = 1 Then";
if (_i==1) { 
 //BA.debugLineNum = 561;BA.debugLine="ii = \"a\"";
_ii = BA.ObjectToChar("a");
 }else if(_i==10) { 
 //BA.debugLineNum = 563;BA.debugLine="ii = \"t\"";
_ii = BA.ObjectToChar("t");
 }else if(_i==11) { 
 //BA.debugLineNum = 565;BA.debugLine="ii = \"j\"";
_ii = BA.ObjectToChar("j");
 }else if(_i==12) { 
 //BA.debugLineNum = 567;BA.debugLine="ii = \"q\"";
_ii = BA.ObjectToChar("q");
 }else if(_i==13) { 
 //BA.debugLineNum = 569;BA.debugLine="ii = \"k\"";
_ii = BA.ObjectToChar("k");
 }else {
 //BA.debugLineNum = 571;BA.debugLine="ii = i";
_ii = BA.ObjectToChar(_i);
 };
 //BA.debugLineNum = 573;BA.debugLine="If (CountChar(hand1,ii) = 4) Then";
if ((_vv2(_hand1,_ii)==4)) { 
 //BA.debugLineNum = 574;BA.debugLine="fourofakind = True";
_fourofakind = anywheresoftware.b4a.keywords.Common.True;
 }else if((_vv2(_hand1,_ii)==3)) { 
 //BA.debugLineNum = 576;BA.debugLine="threeofakind = True";
_threeofakind = anywheresoftware.b4a.keywords.Common.True;
 }else if((_vv2(_hand1,_ii)==2)) { 
 //BA.debugLineNum = 578;BA.debugLine="pairs = pairs + 1";
_pairs = (int) (_pairs+1);
 };
 }
};
 //BA.debugLineNum = 581;BA.debugLine="If ((straight = 2) And (flush = True)) Then";
if (((_straight==2) && (_flush==anywheresoftware.b4a.keywords.Common.True))) { 
 //BA.debugLineNum = 582;BA.debugLine="Return \"Royal Flush\"";
if (true) return "Royal Flush";
 }else if(((_straight==1) && (_flush==anywheresoftware.b4a.keywords.Common.True))) { 
 //BA.debugLineNum = 584;BA.debugLine="Return \"Straight Flush\"";
if (true) return "Straight Flush";
 }else if((_fourofakind==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 586;BA.debugLine="Return \"Four of a Kind\"";
if (true) return "Four of a Kind";
 }else if(((_threeofakind==anywheresoftware.b4a.keywords.Common.True) && (_pairs>0))) { 
 //BA.debugLineNum = 588;BA.debugLine="Return \"Full House\"";
if (true) return "Full House";
 }else if(_flush==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 590;BA.debugLine="Return \"Flush\"";
if (true) return "Flush";
 }else if((_straight>0)) { 
 //BA.debugLineNum = 592;BA.debugLine="Return \"Straight\"";
if (true) return "Straight";
 }else if((_threeofakind==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 594;BA.debugLine="Return \"Three of a Kind\"";
if (true) return "Three of a Kind";
 }else if((_pairs>1)) { 
 //BA.debugLineNum = 596;BA.debugLine="Return \"Two Pair\"";
if (true) return "Two Pair";
 }else if(((_vv2(_hand1,BA.ObjectToChar("a"))==2) || (_vv2(_hand1,BA.ObjectToChar("k"))==2) || (_vv2(_hand1,BA.ObjectToChar("q"))==2) || (_vv2(_hand1,BA.ObjectToChar("j"))==2))) { 
 //BA.debugLineNum = 598;BA.debugLine="Return \"Jacks or Better\"";
if (true) return "Jacks or Better";
 }else {
 //BA.debugLineNum = 600;BA.debugLine="Return \"Try again\"";
if (true) return "Try again";
 };
 //BA.debugLineNum = 603;BA.debugLine="End Sub";
return "";
}
public static String  _vvvv3(String _hand1) throws Exception{
boolean _flush = false;
int _straight = 0;
boolean _fourofakind = false;
boolean _threeofakind = false;
int _pairs = 0;
char _ii = '\0';
int _i = 0;
 //BA.debugLineNum = 482;BA.debugLine="Public Sub returnHandJacksOrBetter(hand1 As String";
 //BA.debugLineNum = 483;BA.debugLine="Dim flush As Boolean";
_flush = false;
 //BA.debugLineNum = 484;BA.debugLine="Dim straight As Int";
_straight = 0;
 //BA.debugLineNum = 485;BA.debugLine="Dim fourofakind As Boolean";
_fourofakind = false;
 //BA.debugLineNum = 486;BA.debugLine="Dim threeofakind As Boolean";
_threeofakind = false;
 //BA.debugLineNum = 487;BA.debugLine="Dim pairs As Int";
_pairs = 0;
 //BA.debugLineNum = 488;BA.debugLine="pairs = 0";
_pairs = (int) (0);
 //BA.debugLineNum = 489;BA.debugLine="straight = 0";
_straight = (int) (0);
 //BA.debugLineNum = 490;BA.debugLine="Dim ii As Char";
_ii = '\0';
 //BA.debugLineNum = 491;BA.debugLine="If ((CountChar(hand1,\"c\")) = 5 Or (CountChar(hand";
if (((_vv2(_hand1,BA.ObjectToChar("c")))==5 || (_vv2(_hand1,BA.ObjectToChar("h")))==5 || (_vv2(_hand1,BA.ObjectToChar("d")))==5 || (_vv2(_hand1,BA.ObjectToChar("s")))==5)) { 
 //BA.debugLineNum = 492;BA.debugLine="flush = True";
_flush = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 494;BA.debugLine="straight = isStraight(hand1)";
_straight = _vvv0(_hand1);
 //BA.debugLineNum = 495;BA.debugLine="If straight = -1 Then";
if (_straight==-1) { 
 //BA.debugLineNum = 496;BA.debugLine="Msgbox(\"Unknown error, please close app and rest";
anywheresoftware.b4a.keywords.Common.Msgbox("Unknown error, please close app and restart.","Error",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 498;BA.debugLine="For i = 1 To 13";
{
final int step16 = 1;
final int limit16 = (int) (13);
for (_i = (int) (1) ; (step16 > 0 && _i <= limit16) || (step16 < 0 && _i >= limit16); _i = ((int)(0 + _i + step16)) ) {
 //BA.debugLineNum = 499;BA.debugLine="If i = 1 Then";
if (_i==1) { 
 //BA.debugLineNum = 500;BA.debugLine="ii = \"a\"";
_ii = BA.ObjectToChar("a");
 }else if(_i==10) { 
 //BA.debugLineNum = 502;BA.debugLine="ii = \"t\"";
_ii = BA.ObjectToChar("t");
 }else if(_i==11) { 
 //BA.debugLineNum = 504;BA.debugLine="ii = \"j\"";
_ii = BA.ObjectToChar("j");
 }else if(_i==12) { 
 //BA.debugLineNum = 506;BA.debugLine="ii = \"q\"";
_ii = BA.ObjectToChar("q");
 }else if(_i==13) { 
 //BA.debugLineNum = 508;BA.debugLine="ii = \"k\"";
_ii = BA.ObjectToChar("k");
 }else {
 //BA.debugLineNum = 510;BA.debugLine="ii = i";
_ii = BA.ObjectToChar(_i);
 };
 //BA.debugLineNum = 512;BA.debugLine="If (CountChar(hand1,ii) = 4) Then";
if ((_vv2(_hand1,_ii)==4)) { 
 //BA.debugLineNum = 513;BA.debugLine="fourofakind = True";
_fourofakind = anywheresoftware.b4a.keywords.Common.True;
 }else if((_vv2(_hand1,_ii)==3)) { 
 //BA.debugLineNum = 515;BA.debugLine="threeofakind = True";
_threeofakind = anywheresoftware.b4a.keywords.Common.True;
 }else if((_vv2(_hand1,_ii)==2)) { 
 //BA.debugLineNum = 517;BA.debugLine="pairs = pairs + 1";
_pairs = (int) (_pairs+1);
 };
 }
};
 //BA.debugLineNum = 520;BA.debugLine="If ((straight = 2) And (flush = True)) Then";
if (((_straight==2) && (_flush==anywheresoftware.b4a.keywords.Common.True))) { 
 //BA.debugLineNum = 521;BA.debugLine="Return \"Royal Flush\"";
if (true) return "Royal Flush";
 }else if(((_straight==1) && (_flush==anywheresoftware.b4a.keywords.Common.True))) { 
 //BA.debugLineNum = 523;BA.debugLine="Return \"Straight Flush\"";
if (true) return "Straight Flush";
 }else if((_fourofakind==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 525;BA.debugLine="Return \"Four of a Kind\"";
if (true) return "Four of a Kind";
 }else if(((_threeofakind==anywheresoftware.b4a.keywords.Common.True) && (_pairs>0))) { 
 //BA.debugLineNum = 527;BA.debugLine="Return \"Full House\"";
if (true) return "Full House";
 }else if(_flush==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 529;BA.debugLine="Return \"Flush\"";
if (true) return "Flush";
 }else if((_straight>0)) { 
 //BA.debugLineNum = 531;BA.debugLine="Return \"Straight\"";
if (true) return "Straight";
 }else if((_threeofakind==anywheresoftware.b4a.keywords.Common.True)) { 
 //BA.debugLineNum = 533;BA.debugLine="Return \"Three of a Kind\"";
if (true) return "Three of a Kind";
 }else if((_pairs>1)) { 
 //BA.debugLineNum = 535;BA.debugLine="Return \"Two Pair\"";
if (true) return "Two Pair";
 }else if(((_vv2(_hand1,BA.ObjectToChar("a"))==2) || (_vv2(_hand1,BA.ObjectToChar("k"))==2) || (_vv2(_hand1,BA.ObjectToChar("q"))==2) || (_vv2(_hand1,BA.ObjectToChar("j"))==2))) { 
 //BA.debugLineNum = 537;BA.debugLine="Return \"Jacks or Better\"";
if (true) return "Jacks or Better";
 }else {
 //BA.debugLineNum = 539;BA.debugLine="Return \"Try again\"";
if (true) return "Try again";
 };
 //BA.debugLineNum = 542;BA.debugLine="End Sub";
return "";
}
public static int  _vvvv2(String _a,int _b,String _h) throws Exception{
 //BA.debugLineNum = 429;BA.debugLine="Sub returnPoints1(a As String, b As Int, h As Stri";
 //BA.debugLineNum = 430;BA.debugLine="If (h=\"jacksorbetter\") Then";
if (((_h).equals("jacksorbetter"))) { 
 //BA.debugLineNum = 431;BA.debugLine="Return returnPointsJacksOrBetter(a,b)";
if (true) return _vvvv5(_a,_b);
 }else if(((_h).equals("allamerican"))) { 
 //BA.debugLineNum = 433;BA.debugLine="Return returnPointsAllAmerican(a,b)";
if (true) return _vvvv6(_a,_b);
 };
 //BA.debugLineNum = 435;BA.debugLine="Return -1";
if (true) return (int) (-1);
 //BA.debugLineNum = 436;BA.debugLine="End Sub";
return 0;
}
public static int  _vvvv6(String _a,int _b) throws Exception{
 //BA.debugLineNum = 673;BA.debugLine="Public Sub returnPointsAllAmerican(a As String, b";
 //BA.debugLineNum = 674;BA.debugLine="If a=\"Royal Flush\" Then";
if ((_a).equals("Royal Flush")) { 
 //BA.debugLineNum = 675;BA.debugLine="If b = 5 Then";
if (_b==5) { 
 //BA.debugLineNum = 676;BA.debugLine="Return 4000";
if (true) return (int) (4000);
 }else {
 //BA.debugLineNum = 678;BA.debugLine="Return (250*b)";
if (true) return (int) ((250*_b));
 };
 }else if((_a).equals("Straight Flush")) { 
 //BA.debugLineNum = 681;BA.debugLine="Return (b*200)";
if (true) return (int) ((_b*200));
 }else if((_a).equals("Four of a Kind")) { 
 //BA.debugLineNum = 683;BA.debugLine="Return (35*b)";
if (true) return (int) ((35*_b));
 }else if((_a).equals("Full House")) { 
 //BA.debugLineNum = 685;BA.debugLine="Return (8*b)";
if (true) return (int) ((8*_b));
 }else if((_a).equals("Flush")) { 
 //BA.debugLineNum = 687;BA.debugLine="Return (8 * b)";
if (true) return (int) ((8*_b));
 }else if((_a).equals("Straight")) { 
 //BA.debugLineNum = 689;BA.debugLine="Return (8*b)";
if (true) return (int) ((8*_b));
 }else if((_a).equals("Three of a Kind")) { 
 //BA.debugLineNum = 691;BA.debugLine="Return (3*b)";
if (true) return (int) ((3*_b));
 }else if((_a).equals("Two Pair")) { 
 //BA.debugLineNum = 693;BA.debugLine="Return (1 *b)";
if (true) return (int) ((1*_b));
 }else if((_a).equals("Jacks or Better")) { 
 //BA.debugLineNum = 695;BA.debugLine="Return (1 * b)";
if (true) return (int) ((1*_b));
 }else if((_a).equals("Try again")) { 
 //BA.debugLineNum = 697;BA.debugLine="Return 0";
if (true) return (int) (0);
 };
 //BA.debugLineNum = 699;BA.debugLine="Return 0";
if (true) return (int) (0);
 //BA.debugLineNum = 700;BA.debugLine="End Sub";
return 0;
}
public static int  _vvvv5(String _a,int _b) throws Exception{
 //BA.debugLineNum = 645;BA.debugLine="Public Sub returnPointsJacksOrBetter(a As String,";
 //BA.debugLineNum = 646;BA.debugLine="If a=\"Royal Flush\" Then";
if ((_a).equals("Royal Flush")) { 
 //BA.debugLineNum = 647;BA.debugLine="If b = 5 Then";
if (_b==5) { 
 //BA.debugLineNum = 648;BA.debugLine="Return 4000";
if (true) return (int) (4000);
 }else {
 //BA.debugLineNum = 650;BA.debugLine="Return 250";
if (true) return (int) (250);
 };
 }else if((_a).equals("Straight Flush")) { 
 //BA.debugLineNum = 653;BA.debugLine="Return (b*50)";
if (true) return (int) ((_b*50));
 }else if((_a).equals("Four of a Kind")) { 
 //BA.debugLineNum = 655;BA.debugLine="Return (25*b)";
if (true) return (int) ((25*_b));
 }else if((_a).equals("Full House")) { 
 //BA.debugLineNum = 657;BA.debugLine="Return (9*b)";
if (true) return (int) ((9*_b));
 }else if((_a).equals("Flush")) { 
 //BA.debugLineNum = 659;BA.debugLine="Return (6 * b)";
if (true) return (int) ((6*_b));
 }else if((_a).equals("Straight")) { 
 //BA.debugLineNum = 661;BA.debugLine="Return (b*4)";
if (true) return (int) ((_b*4));
 }else if((_a).equals("Three of a Kind")) { 
 //BA.debugLineNum = 663;BA.debugLine="Return (3*b)";
if (true) return (int) ((3*_b));
 }else if((_a).equals("Two Pair")) { 
 //BA.debugLineNum = 665;BA.debugLine="Return (2 *b)";
if (true) return (int) ((2*_b));
 }else if((_a).equals("Jacks or Better")) { 
 //BA.debugLineNum = 667;BA.debugLine="Return (1 * b)";
if (true) return (int) ((1*_b));
 }else if((_a).equals("Try again")) { 
 //BA.debugLineNum = 669;BA.debugLine="Return 0";
if (true) return (int) (0);
 };
 //BA.debugLineNum = 671;BA.debugLine="Return 0";
if (true) return (int) (0);
 //BA.debugLineNum = 672;BA.debugLine="End Sub";
return 0;
}
public static String  _v0() throws Exception{
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _tw = null;
 //BA.debugLineNum = 21;BA.debugLine="Sub SaveOptions";
 //BA.debugLineNum = 22;BA.debugLine="Dim TW As TextWriter";
_tw = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 23;BA.debugLine="TW.Initialize(File.OpenOutput(File.DirInternal";
_tw.Initialize((java.io.OutputStream)(anywheresoftware.b4a.keywords.Common.File.OpenOutput(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"[file name]",anywheresoftware.b4a.keywords.Common.False).getObject()));
 //BA.debugLineNum = 24;BA.debugLine="TW.WriteLine(score)";
_tw.WriteLine(BA.NumberToString(_v6));
 //BA.debugLineNum = 25;BA.debugLine="TW.Close";
_tw.Close();
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public static String  _settingsback_click() throws Exception{
 //BA.debugLineNum = 819;BA.debugLine="Sub settingsback_Click";
 //BA.debugLineNum = 820;BA.debugLine="settingsscroller.Visible=False";
mostCurrent._settingsscroller.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 821;BA.debugLine="settingsback.Visible =False";
mostCurrent._settingsback.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 822;BA.debugLine="Activity_Create(False)";
_activity_create(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 823;BA.debugLine="End Sub";
return "";
}
public static String  _settingsfeedback_click() throws Exception{
anywheresoftware.b4a.phone.Phone.Email _message = null;
 //BA.debugLineNum = 824;BA.debugLine="Sub settingsfeedback_Click";
 //BA.debugLineNum = 825;BA.debugLine="Dim Message As Email";
_message = new anywheresoftware.b4a.phone.Phone.Email();
 //BA.debugLineNum = 826;BA.debugLine="Message.To.Add(\"ethanuzarowskiapps@gmail.com\")";
_message.To.Add((Object)("ethanuzarowskiapps@gmail.com"));
 //BA.debugLineNum = 827;BA.debugLine="Message.Subject=\"Ultimate Video Poker Feedback\"";
_message.Subject = "Ultimate Video Poker Feedback";
 //BA.debugLineNum = 828;BA.debugLine="StartActivity(Message.GetIntent)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_message.GetIntent()));
 //BA.debugLineNum = 829;BA.debugLine="End Sub";
return "";
}
public static String  _settingsotherapps_click() throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _otherappsintent = null;
 //BA.debugLineNum = 837;BA.debugLine="Sub settingsotherapps_Click";
 //BA.debugLineNum = 838;BA.debugLine="Dim otherappsintent As Intent";
_otherappsintent = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 839;BA.debugLine="otherappsintent.Initialize(otherappsintent.ACTION_";
_otherappsintent.Initialize(_otherappsintent.ACTION_VIEW,"market://search?q=Ethan Uzarowski".replace("~",anywheresoftware.b4a.keywords.Common.QUOTE));
 //BA.debugLineNum = 840;BA.debugLine="StartActivity(otherappsintent)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_otherappsintent.getObject()));
 //BA.debugLineNum = 841;BA.debugLine="End Sub";
return "";
}
public static String  _settingsreview_click() throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _googleplay = null;
 //BA.debugLineNum = 830;BA.debugLine="Sub settingsreview_Click";
 //BA.debugLineNum = 831;BA.debugLine="Private GooglePlay As Intent";
_googleplay = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 833;BA.debugLine="GooglePlay.Initialize(GooglePlay.ACTION_VIEW, \"mar";
_googleplay.Initialize(_googleplay.ACTION_VIEW,"market://details?id=com.ethanuzarowski.videopoker");
 //BA.debugLineNum = 835;BA.debugLine="StartActivity(GooglePlay)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_googleplay.getObject()));
 //BA.debugLineNum = 836;BA.debugLine="End Sub";
return "";
}
}
