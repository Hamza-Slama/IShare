package ia2.moduleproject.eniso.ishare.Utils

/**
 * Created by Hamza on 23/02/2018.
 */
fun SplitString(email:String):String{
    val split= email.split("@")
    return split[0]
}