package enteties

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Basic
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id

import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
class Worker(
    @Id
    @GeneratedValue
    var id: Long = 0,

    @Column(name = "first_name", length = 127)
    var name: String,

    @NaturalId
    var email: String,

    @Enumerated(value = EnumType.STRING)
    var workingType: WorkingType,

    var birthDate: LocalDate,
    var personalData: PersonalData,
    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    var homeAddress:HomeAddress,
    @OneToMany(cascade = arrayOf(CascadeType.ALL))
    var Kids: MutableSet<Kid>,
    @CreationTimestamp
    var createdTime: LocalDateTime? = null,
    @UpdateTimestamp
    var updatedTime: LocalDateTime? = null
) {
    override fun toString(): String {
        return "Worker(id=$id, name='$name', email='$email', workingType=$workingType, birthDate=$birthDate)"
    }
}

enum class WorkingType {
    PART_TIME,
    FULL_TIME
}