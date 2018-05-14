package ia2.moduleproject.eniso.ishare.Model

/**
 * Created by hamza on 5/8/18.
 */
class  SharesModel {
    var sharesID: String? = null
    var sharesText: String? = null
    var sharesImageURL: String? = null
    var sharesDate: String? = null
    var personName: String? = null
    var personImage: String? = null
    var personID: String? = null

    constructor(sharesID: String, sharesText: String, sharesImageURL: String,
                sharesDate: String, personName: String, personImage: String, personID: String) {
        this.sharesID = sharesID
        this.sharesText = sharesText
        this.sharesImageURL = sharesImageURL
        this.sharesDate = sharesDate
        this.personName = personName
        this.personImage = personImage
        this.personID = personID
    }
}