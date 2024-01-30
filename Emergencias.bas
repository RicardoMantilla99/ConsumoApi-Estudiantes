B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Class
Version=12
@EndOfDesignText@
Sub Class_Globals
	Private url As String = "https://restapi-estudiantes-5ghy.onrender.com"
	Private req As HttpJob
	Private deserializador As JSONParser
	Private serializador As JSONGenerator
End Sub

'Initializes the object. You can add parameters to this method if needed.
Public Sub Initialize
	req.Initialize("", Me)
End Sub
Public Sub SelectOne(id As Int) As ResumableSub
	Dim p As Emergencia
	p.Initialize
	
	req.Download(url & "/api/estudiantes" & id)
	Wait For (req) JobDone(res As HttpJob)
	
	If res.Success Then
		deserializador.Initialize(res.GetString)
		Dim datos As Map
		datos = deserializador.NextObject
		p.id = datos.Get("id")
		p.nombre = datos.Get("nombre")
		p.apellido = datos.Get("apellido")
		p.edad = datos.get("edad")
		p.Initialize= datos.Get("inscripcion")
	End If
	res.Release
	Return p
End Sub

Public Sub Insert(id As Int, nombre As String, apellido As String, edad As String, inscripcion As String) As ResumableSub
	Dim p As Emergencia
	p.Initialize
	
	Dim m As Map
	m.Initialize
	
	m.Put("id" , id)
	m.Put("nombre" , nombre)
	m.Put("apellido",apellido)
	m.Put("edad", edad)
	m.Put("inscripcion", inscripcion)
	
	
	serializador.Initialize(m)
	Dim json As String = serializador.ToString
	
	req.PostString(url, json)
	req.GetRequest.SetContentType("application/json")
	
	Wait For (req) JobDone( res As HttpJob)
	If res.Success Then
		deserializador.Initialize(res.Getstring)
		m = deserializador.NextObject
		p.id = m.Get("id")
		p.nombre = nombre
		p.apellido = apellido
		p.edad = edad
		p.inscripcion = inscripcion
	End If
	res.Release
	Return p
End Sub

public Sub Update(id As Int, nombre As String, apellido As String, edad As String, inscripcion As String) As ResumableSub
	Dim m As Map
	m.Initialize
	
	m.Put("id" , id)
	m.Put("nombre" , nombre)
	m.Put("apellido", apellido)
	m.Put("edad", edad)
	m.Put("inscripcion", inscripcion)
	
	
	serializador.Initialize(m)
	Dim json As String = serializador.ToString
	
	req.PutString(url & "/api/estudiantes/" & id, json)
	req.GetRequest.SetContentType("application/json")
	
	Wait For (req) JobDone( res As HttpJob)
	Return res.Success
End Sub

Public Sub Delete(id As Int) As ResumableSub
	req.Delete( url & "/api/estudiantes/" & id)
	Wait For (req) JobDone( res As HttpJob)
	Return res.Success
End Sub