package ia2.moduleproject.eniso.ishare.Utils

/**
 * Created by Hamza on 23/02/2018.
 */
fun SplitString(email:String):String{
    val split= email.split("@")
    return split[0]
}

val localhost = "http://192.168.14.5"

//val localhost = "http://192.168.1.64"