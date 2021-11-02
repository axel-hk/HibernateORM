package enteties

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
class Kid (
    @Id
    @GeneratedValue
    var id: Long = 0,
    var name: String,
    var serName: String,
    var sex: String

)