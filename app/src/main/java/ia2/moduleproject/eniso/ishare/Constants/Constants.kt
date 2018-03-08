package ia2.moduleproject.eniso.ishare.Constants

/**
 * Created by Hamza on 23/02/2018.
 */

var MyREPONSE = "MyREPONSE"
var MyUserLoginAndPassword = "MyUserLoginAndPassword "
fun SplitString(email:String):String{
    val split= email.split("@")
    return split[0]
}