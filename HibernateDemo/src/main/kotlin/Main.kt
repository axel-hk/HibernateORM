//import enteties.HomeAddress
import enteties.*
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import java.time.LocalDate

fun main() {
    val sessionFactory = Configuration().configure()
        .addAnnotatedClass(Worker::class.java)
        .addAnnotatedClass(HomeAddress::class.java)
        .addAnnotatedClass(Kid::class.java)
        .buildSessionFactory()

    sessionFactory.use { sessionFactory ->
        val dao = WorkerDAO(sessionFactory)

        val worker1 = Worker(
            name = "Petr",
            email = "petr@worker.ru",
            workingType = WorkingType.FULL_TIME,
            birthDate = LocalDate.now().minusYears(20),
            personalData = PersonalData("123", "74839"),
            homeAddress = HomeAddress(street = "Кутузовский пр-т", homeNumber = 12),
            Kids = mutableSetOf(Kid(name = "Alex", serName = "Ann", sex = "Male"), Kid(name="Ann", serName = "Ann", sex = "Female"))
        )
        val worker2 = Worker(
            name = "Ivan",
            email = "ivan@sworker.ru",
            workingType = WorkingType.PART_TIME,
            birthDate = LocalDate.now().minusYears(24),
            personalData = PersonalData("543", "341444"),
            homeAddress = HomeAddress(street = "Ленина", homeNumber = 75),
            Kids = mutableSetOf(Kid(name = "Mary", serName = "Beaty", sex = "Female"))
        )

        dao.save(worker1)

        dao.save(worker2)

        var found = dao.find(worker1.id)
        println("Найден работник: $found \n")

        found = dao.find(worker2.email)
        println("Найден работник: $found \n")

        val allWorkers = dao.findAll()
        println("все работники: $allWorkers")

    }
}

class WorkerDAO(
    private val sessionFactory: SessionFactory
) {
    fun save(worker: Worker) {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.save(worker)
            session.transaction.commit()
        }
    }

    fun find(id: Long): Worker? {
        val result: Worker?
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.get(Worker::class.java, id)
            session.transaction.commit()
        }
        return result
    }

    fun find(email: String):Worker? {
        val result: Worker?
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result =
                session.byNaturalId(Worker::class.java).using("email", email).loadOptional().orElse(null)
            session.transaction.commit()
        }
        return result
    }

    fun findAll(): List<Worker> {
        val result: List<Worker>
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.createQuery("from Worker").list() as List<Worker>
            session.transaction.commit()
        }
        return result
    }
}