package b4a.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class emergencias extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "b4a.example.emergencias");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", b4a.example.emergencias.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public String _url = "";
public b4a.example.httpjob _req = null;
public anywheresoftware.b4a.objects.collections.JSONParser _deserializador = null;
public anywheresoftware.b4a.objects.collections.JSONParser.JSONGenerator _serializador = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.httputils2service _httputils2service = null;
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 1;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 2;BA.debugLine="Private url As String = \"https://63eeddf438892015";
_url = "https://63eeddf4388920150de56e0e.mockapi.io/api/v1/eme_emergencia";
 //BA.debugLineNum = 3;BA.debugLine="Private req As HttpJob";
_req = new b4a.example.httpjob();
 //BA.debugLineNum = 4;BA.debugLine="Private deserializador As JSONParser";
_deserializador = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 5;BA.debugLine="Private serializador As JSONGenerator";
_serializador = new anywheresoftware.b4a.objects.collections.JSONParser.JSONGenerator();
 //BA.debugLineNum = 6;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _delete(int _id) throws Exception{
ResumableSub_Delete rsub = new ResumableSub_Delete(this,_id);
rsub.resume(ba, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_Delete extends BA.ResumableSub {
public ResumableSub_Delete(b4a.example.emergencias parent,int _id) {
this.parent = parent;
this._id = _id;
}
b4a.example.emergencias parent;
int _id;
b4a.example.httpjob _res = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
parent.__c.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = -1;
 //BA.debugLineNum = 89;BA.debugLine="req.Delete( url & \"/\" & id)";
parent._req._delete /*String*/ (parent._url+"/"+BA.NumberToString(_id));
 //BA.debugLineNum = 90;BA.debugLine="Wait For (req) JobDone( res As HttpJob)";
parent.__c.WaitFor("jobdone", ba, this, (Object)(parent._req));
this.state = 1;
return;
case 1:
//C
this.state = -1;
_res = (b4a.example.httpjob) result[0];
;
 //BA.debugLineNum = 91;BA.debugLine="Return res.Success";
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(_res._success /*boolean*/ ));return;};
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _jobdone(b4a.example.httpjob _res) throws Exception{
}
public String  _initialize(anywheresoftware.b4a.BA _ba) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 9;BA.debugLine="Public Sub Initialize";
 //BA.debugLineNum = 10;BA.debugLine="req.Initialize(\"\", Me)";
_req._initialize /*String*/ (ba,"",this);
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _insert(int _id,String _mensaje,String _fecha,String _lugar,String _estado) throws Exception{
ResumableSub_Insert rsub = new ResumableSub_Insert(this,_id,_mensaje,_fecha,_lugar,_estado);
rsub.resume(ba, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_Insert extends BA.ResumableSub {
public ResumableSub_Insert(b4a.example.emergencias parent,int _id,String _mensaje,String _fecha,String _lugar,String _estado) {
this.parent = parent;
this._id = _id;
this._mensaje = _mensaje;
this._fecha = _fecha;
this._lugar = _lugar;
this._estado = _estado;
}
b4a.example.emergencias parent;
int _id;
String _mensaje;
String _fecha;
String _lugar;
String _estado;
b4a.example.emergencia _p = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
String _json = "";
b4a.example.httpjob _res = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
parent.__c.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 34;BA.debugLine="Dim p As Emergencia";
_p = new b4a.example.emergencia();
 //BA.debugLineNum = 35;BA.debugLine="p.Initialize";
_p._initialize /*String*/ (ba);
 //BA.debugLineNum = 37;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 38;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 40;BA.debugLine="m.Put(\"id\" , id)";
_m.Put((Object)("id"),(Object)(_id));
 //BA.debugLineNum = 41;BA.debugLine="m.Put(\"mensaje\" , mensaje)";
_m.Put((Object)("mensaje"),(Object)(_mensaje));
 //BA.debugLineNum = 42;BA.debugLine="m.Put(\"fecha\",fecha)";
_m.Put((Object)("fecha"),(Object)(_fecha));
 //BA.debugLineNum = 43;BA.debugLine="m.Put(\"lugar\", lugar)";
_m.Put((Object)("lugar"),(Object)(_lugar));
 //BA.debugLineNum = 44;BA.debugLine="m.Put(\"estado\", estado)";
_m.Put((Object)("estado"),(Object)(_estado));
 //BA.debugLineNum = 47;BA.debugLine="serializador.Initialize(m)";
parent._serializador.Initialize(_m);
 //BA.debugLineNum = 48;BA.debugLine="Dim json As String = serializador.ToString";
_json = parent._serializador.ToString();
 //BA.debugLineNum = 50;BA.debugLine="req.PostString(url, json)";
parent._req._poststring /*String*/ (parent._url,_json);
 //BA.debugLineNum = 51;BA.debugLine="req.GetRequest.SetContentType(\"application/json\")";
parent._req._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetContentType("application/json");
 //BA.debugLineNum = 53;BA.debugLine="Wait For (req) JobDone( res As HttpJob)";
parent.__c.WaitFor("jobdone", ba, this, (Object)(parent._req));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_res = (b4a.example.httpjob) result[0];
;
 //BA.debugLineNum = 54;BA.debugLine="If res.Success Then";
if (true) break;

case 1:
//if
this.state = 4;
if (_res._success /*boolean*/ ) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 55;BA.debugLine="deserializador.Initialize(res.Getstring)";
parent._deserializador.Initialize(_res._getstring /*String*/ ());
 //BA.debugLineNum = 56;BA.debugLine="m = deserializador.NextObject";
_m = parent._deserializador.NextObject();
 //BA.debugLineNum = 57;BA.debugLine="p.id = m.Get(\"id\")";
_p._id /*int*/  = (int)(BA.ObjectToNumber(_m.Get((Object)("id"))));
 //BA.debugLineNum = 58;BA.debugLine="p.mensaje = mensaje";
_p._mensaje /*String*/  = _mensaje;
 //BA.debugLineNum = 59;BA.debugLine="p.fecha = fecha";
_p._fecha /*String*/  = _fecha;
 //BA.debugLineNum = 60;BA.debugLine="p.lugar = lugar";
_p._lugar /*String*/  = _lugar;
 //BA.debugLineNum = 61;BA.debugLine="p.estado = estado";
_p._estado /*String*/  = _estado;
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 63;BA.debugLine="res.Release";
_res._release /*String*/ ();
 //BA.debugLineNum = 64;BA.debugLine="Return p";
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(_p));return;};
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _selectone(int _id) throws Exception{
ResumableSub_SelectOne rsub = new ResumableSub_SelectOne(this,_id);
rsub.resume(ba, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_SelectOne extends BA.ResumableSub {
public ResumableSub_SelectOne(b4a.example.emergencias parent,int _id) {
this.parent = parent;
this._id = _id;
}
b4a.example.emergencias parent;
int _id;
b4a.example.emergencia _p = null;
b4a.example.httpjob _res = null;
anywheresoftware.b4a.objects.collections.Map _datos = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
parent.__c.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 13;BA.debugLine="Dim p As Emergencia";
_p = new b4a.example.emergencia();
 //BA.debugLineNum = 14;BA.debugLine="p.Initialize";
_p._initialize /*String*/ (ba);
 //BA.debugLineNum = 16;BA.debugLine="req.Download(url & \"/\" & id)";
parent._req._download /*String*/ (parent._url+"/"+BA.NumberToString(_id));
 //BA.debugLineNum = 17;BA.debugLine="Wait For (req) JobDone(res As HttpJob)";
parent.__c.WaitFor("jobdone", ba, this, (Object)(parent._req));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_res = (b4a.example.httpjob) result[0];
;
 //BA.debugLineNum = 19;BA.debugLine="If res.Success Then";
if (true) break;

case 1:
//if
this.state = 4;
if (_res._success /*boolean*/ ) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 20;BA.debugLine="deserializador.Initialize(res.GetString)";
parent._deserializador.Initialize(_res._getstring /*String*/ ());
 //BA.debugLineNum = 21;BA.debugLine="Dim datos As Map";
_datos = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 22;BA.debugLine="datos = deserializador.NextObject";
_datos = parent._deserializador.NextObject();
 //BA.debugLineNum = 23;BA.debugLine="p.id = datos.Get(\"id\")";
_p._id /*int*/  = (int)(BA.ObjectToNumber(_datos.Get((Object)("id"))));
 //BA.debugLineNum = 24;BA.debugLine="p.mensaje = datos.Get(\"mensaje\")";
_p._mensaje /*String*/  = BA.ObjectToString(_datos.Get((Object)("mensaje")));
 //BA.debugLineNum = 25;BA.debugLine="p.fecha = datos.Get(\"fecha\")";
_p._fecha /*String*/  = BA.ObjectToString(_datos.Get((Object)("fecha")));
 //BA.debugLineNum = 26;BA.debugLine="p.lugar = datos.get(\"lugar\")";
_p._lugar /*String*/  = BA.ObjectToString(_datos.Get((Object)("lugar")));
 //BA.debugLineNum = 27;BA.debugLine="p.estado= datos.Get(\"estado\")";
_p._estado /*String*/  = BA.ObjectToString(_datos.Get((Object)("estado")));
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 29;BA.debugLine="res.Release";
_res._release /*String*/ ();
 //BA.debugLineNum = 30;BA.debugLine="Return p";
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(_p));return;};
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _update(int _id,String _mensaje,String _fecha,String _lugar,String _estado) throws Exception{
ResumableSub_Update rsub = new ResumableSub_Update(this,_id,_mensaje,_fecha,_lugar,_estado);
rsub.resume(ba, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_Update extends BA.ResumableSub {
public ResumableSub_Update(b4a.example.emergencias parent,int _id,String _mensaje,String _fecha,String _lugar,String _estado) {
this.parent = parent;
this._id = _id;
this._mensaje = _mensaje;
this._fecha = _fecha;
this._lugar = _lugar;
this._estado = _estado;
}
b4a.example.emergencias parent;
int _id;
String _mensaje;
String _fecha;
String _lugar;
String _estado;
anywheresoftware.b4a.objects.collections.Map _m = null;
String _json = "";
b4a.example.httpjob _res = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
parent.__c.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = -1;
 //BA.debugLineNum = 68;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 69;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 71;BA.debugLine="m.Put(\"id\" , id)";
_m.Put((Object)("id"),(Object)(_id));
 //BA.debugLineNum = 72;BA.debugLine="m.Put(\"mensaje\" , mensaje)";
_m.Put((Object)("mensaje"),(Object)(_mensaje));
 //BA.debugLineNum = 73;BA.debugLine="m.Put(\"fecha\",fecha)";
_m.Put((Object)("fecha"),(Object)(_fecha));
 //BA.debugLineNum = 74;BA.debugLine="m.Put(\"lugar\", lugar)";
_m.Put((Object)("lugar"),(Object)(_lugar));
 //BA.debugLineNum = 75;BA.debugLine="m.Put(\"estado\", estado)";
_m.Put((Object)("estado"),(Object)(_estado));
 //BA.debugLineNum = 78;BA.debugLine="serializador.Initialize(m)";
parent._serializador.Initialize(_m);
 //BA.debugLineNum = 79;BA.debugLine="Dim json As String = serializador.ToString";
_json = parent._serializador.ToString();
 //BA.debugLineNum = 81;BA.debugLine="req.PutString(url & \"/\" & id, json)";
parent._req._putstring /*String*/ (parent._url+"/"+BA.NumberToString(_id),_json);
 //BA.debugLineNum = 82;BA.debugLine="req.GetRequest.SetContentType(\"application/json\")";
parent._req._getrequest /*anywheresoftware.b4h.okhttp.OkHttpClientWrapper.OkHttpRequest*/ ().SetContentType("application/json");
 //BA.debugLineNum = 84;BA.debugLine="Wait For (req) JobDone( res As HttpJob)";
parent.__c.WaitFor("jobdone", ba, this, (Object)(parent._req));
this.state = 1;
return;
case 1:
//C
this.state = -1;
_res = (b4a.example.httpjob) result[0];
;
 //BA.debugLineNum = 85;BA.debugLine="Return res.Success";
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(_res._success /*boolean*/ ));return;};
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
