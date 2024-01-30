package b4a.example;


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
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.main");
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
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create " + (isFirst ? "(first time)" : "") + " **");
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
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
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
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
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
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
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
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        if (!dontPause)
            BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (main) Pause event (activity is not paused). **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        if (!dontPause) {
            processBA.setActivityPaused(true);
            mostCurrent = null;
        }

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
            main mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
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
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtid = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmensaje = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtfecha = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtlugar = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtestado = null;
public b4a.example.emergencias _eme = null;
public b4a.example.starter _starter = null;
public b4a.example.httputils2service _httputils2service = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 32;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 33;BA.debugLine="Activity.LoadLayout(\"Layout\")";
mostCurrent._activity.LoadLayout("Layout",mostCurrent.activityBA);
 //BA.debugLineNum = 34;BA.debugLine="eme.Initialize";
mostCurrent._eme._initialize /*String*/ (processBA);
 //BA.debugLineNum = 35;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 41;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 37;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 39;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
 //BA.debugLineNum = 45;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 46;BA.debugLine="xui.MsgboxAsync(\"Hello GIOVANY!\", \"B4X\")";
_xui.MsgboxAsync(processBA,BA.ObjectToCharSequence("Hello GIOVANY!"),BA.ObjectToCharSequence("B4X"));
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return "";
}
public static void  _cmddelete_click() throws Exception{
ResumableSub_cmdDelete_Click rsub = new ResumableSub_cmdDelete_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_cmdDelete_Click extends BA.ResumableSub {
public ResumableSub_cmdDelete_Click(b4a.example.main parent) {
this.parent = parent;
}
b4a.example.main parent;
boolean _r = false;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 50;BA.debugLine="Wait For (eme.Delete(txtId.Text)) Complete( r As";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, parent.mostCurrent._eme._delete /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ ((int)(Double.parseDouble(parent.mostCurrent._txtid.getText()))));
this.state = 7;
return;
case 7:
//C
this.state = 1;
_r = (Boolean) result[0];
;
 //BA.debugLineNum = 51;BA.debugLine="If r Then";
if (true) break;

case 1:
//if
this.state = 6;
if (_r) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 52;BA.debugLine="Msgbox(\"registro eliminado \", \"Ok\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("registro eliminado "),BA.ObjectToCharSequence("Ok"),mostCurrent.activityBA);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 54;BA.debugLine="Msgbox(\"registro no eliminado \", \"ERROR !!!\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("registro no eliminado "),BA.ObjectToCharSequence("ERROR !!!"),mostCurrent.activityBA);
 if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _complete(boolean _r) throws Exception{
}
public static void  _cmdinsert_click() throws Exception{
ResumableSub_cmdInsert_Click rsub = new ResumableSub_cmdInsert_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_cmdInsert_Click extends BA.ResumableSub {
public ResumableSub_cmdInsert_Click(b4a.example.main parent) {
this.parent = parent;
}
b4a.example.main parent;
b4a.example.emergencia _e = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 68;BA.debugLine="Wait For (eme.Insert(txtId.Text,txtMensaje.Text,";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, parent.mostCurrent._eme._insert /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ ((int)(Double.parseDouble(parent.mostCurrent._txtid.getText())),parent.mostCurrent._txtmensaje.getText(),parent.mostCurrent._txtfecha.getText(),parent.mostCurrent._txtlugar.getText(),parent.mostCurrent._txtestado.getText()));
this.state = 7;
return;
case 7:
//C
this.state = 1;
_e = (b4a.example.emergencia) result[0];
;
 //BA.debugLineNum = 69;BA.debugLine="If e.id <> 0 Then";
if (true) break;

case 1:
//if
this.state = 6;
if (_e._id /*int*/ !=0) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 70;BA.debugLine="Msgbox(\"datos insertados\", \"Ok\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("datos insertados"),BA.ObjectToCharSequence("Ok"),mostCurrent.activityBA);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 72;BA.debugLine="Msgbox(\"datos NO insertados\", \"ERROR !!!!\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("datos NO insertados"),BA.ObjectToCharSequence("ERROR !!!!"),mostCurrent.activityBA);
 if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 74;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _cmdselectone_click() throws Exception{
ResumableSub_cmdSelectOne_Click rsub = new ResumableSub_cmdSelectOne_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_cmdSelectOne_Click extends BA.ResumableSub {
public ResumableSub_cmdSelectOne_Click(b4a.example.main parent) {
this.parent = parent;
}
b4a.example.main parent;
b4a.example.emergencia _p = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 77;BA.debugLine="Wait For (eme.SelectOne(txtId.Text)) Complete(p A";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, parent.mostCurrent._eme._selectone /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ ((int)(Double.parseDouble(parent.mostCurrent._txtid.getText()))));
this.state = 7;
return;
case 7:
//C
this.state = 1;
_p = (b4a.example.emergencia) result[0];
;
 //BA.debugLineNum = 78;BA.debugLine="If p.id > 0 Then";
if (true) break;

case 1:
//if
this.state = 6;
if (_p._id /*int*/ >0) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 79;BA.debugLine="txtId.Text = p.id";
parent.mostCurrent._txtid.setText(BA.ObjectToCharSequence(_p._id /*int*/ ));
 //BA.debugLineNum = 80;BA.debugLine="txtMensaje.Text = p.mensaje";
parent.mostCurrent._txtmensaje.setText(BA.ObjectToCharSequence(_p._mensaje /*String*/ ));
 //BA.debugLineNum = 81;BA.debugLine="txtFecha.Text = p.fecha";
parent.mostCurrent._txtfecha.setText(BA.ObjectToCharSequence(_p._fecha /*String*/ ));
 //BA.debugLineNum = 82;BA.debugLine="txtLugar.Text = p.lugar";
parent.mostCurrent._txtlugar.setText(BA.ObjectToCharSequence(_p._lugar /*String*/ ));
 //BA.debugLineNum = 83;BA.debugLine="txtEstado.Text = p.estado";
parent.mostCurrent._txtestado.setText(BA.ObjectToCharSequence(_p._estado /*String*/ ));
 //BA.debugLineNum = 85;BA.debugLine="Msgbox(\"datos encontrados\",\"\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("datos encontrados"),BA.ObjectToCharSequence(""),mostCurrent.activityBA);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 87;BA.debugLine="Msgbox(\"datos no encontrados\",\"ERROR\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("datos no encontrados"),BA.ObjectToCharSequence("ERROR"),mostCurrent.activityBA);
 if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _cmdupdate_click() throws Exception{
ResumableSub_cmdUpdate_Click rsub = new ResumableSub_cmdUpdate_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_cmdUpdate_Click extends BA.ResumableSub {
public ResumableSub_cmdUpdate_Click(b4a.example.main parent) {
this.parent = parent;
}
b4a.example.main parent;
boolean _e = false;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 59;BA.debugLine="Wait For (eme.Update(txtId.Text, txtMensaje.Text,";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, parent.mostCurrent._eme._update /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ ((int)(Double.parseDouble(parent.mostCurrent._txtid.getText())),parent.mostCurrent._txtmensaje.getText(),parent.mostCurrent._txtfecha.getText(),parent.mostCurrent._txtlugar.getText(),parent.mostCurrent._txtestado.getText()));
this.state = 7;
return;
case 7:
//C
this.state = 1;
_e = (Boolean) result[0];
;
 //BA.debugLineNum = 60;BA.debugLine="If e Then";
if (true) break;

case 1:
//if
this.state = 6;
if (_e) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 61;BA.debugLine="Msgbox(\"Datos actualizados\", \"Ok\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Datos actualizados"),BA.ObjectToCharSequence("Ok"),mostCurrent.activityBA);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 63;BA.debugLine="Msgbox(\"Datos NO actualizados\", \"ERROR !!!!\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Datos NO actualizados"),BA.ObjectToCharSequence("ERROR !!!!"),mostCurrent.activityBA);
 if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 22;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 24;BA.debugLine="Private txtId As EditText";
mostCurrent._txtid = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private txtMensaje As EditText";
mostCurrent._txtmensaje = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private txtFecha As EditText";
mostCurrent._txtfecha = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private txtLugar As EditText";
mostCurrent._txtlugar = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private txtEstado As EditText";
mostCurrent._txtestado = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private eme As Emergencias";
mostCurrent._eme = new b4a.example.emergencias();
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
starter._process_globals();
httputils2service._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 18;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
}
