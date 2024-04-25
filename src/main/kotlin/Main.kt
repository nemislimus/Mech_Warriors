
class Gun(private val model: String, private val ammo: Int, private val damage: Int) {

    private var ammoCount = ammo

    fun fire(): Int {
        if (ammoCount <= 0) {
            println("$model на перезарядке!")
            reloadAmmo()
        } else {
            ammoCount -=1
            println("Выстрел из $model!")
            return damage
        }
        return 0 //При перезарядке выстрел пропускается
    }

    fun getAmmoInfo() {
        println("Пушка $model. Осталось зарядов: $ammoCount")
    }

    private fun reloadAmmo() {
        ammoCount = ammo
    }

    init {
        this.reloadAmmo()
    }
}

class MechWarrior(private val name: String, private var healthPoint: Int, val rightGun: Gun, val leftGun: Gun) {

    var isAlive: Boolean = true

    fun attackMech(mech: MechWarrior) {
        println("!!!!!$name атакует ${mech.name}!!!!!")
        mech.takeDamage(rightGun.fire())
        mech.takeDamage(leftGun.fire())
        println()
    }

    fun getName():String = name

    private fun takeDamage(damage: Int) {
        healthPoint -= damage
        println("$name получил $damage урона...")
        if (healthPoint <= 0) {
            isAlive = false
            lifeStatus(isAlive)
        } else {
            lifeStatus(isAlive)
        }
    }

    private fun lifeStatus(isAlive: Boolean) {
        if (isAlive) {
            println("Очки здоровья $name: $healthPoint")
        } else {
            println("$name уничтожен!")
        }
    }
}

object Duel {

    fun fight(vararg mechs: MechWarrior): MechWarrior {    // return dead Mech
        var round = 1

        while (true) {
            println("$round РАУНД\n")
            for (striker in mechs) { // attack round

                for (defender in mechs) {

                    if (defender != striker) {
                        striker.attackMech(defender)
                        if (!stillAlive(defender)) return defender
                    } else continue
                }
            }
            round++
            Thread.sleep(1500)
        }
    }


    private fun stillAlive(mech: MechWarrior): Boolean { // check for dead Mech
        if (mech.isAlive == false) {
            println("Битва окончена!")
            return false
        }
        return true
    }

}

fun main() {

    //create 4 guns
    println("Создаём пушки и заряжаем их!")
    val laser = Gun("Laser", 15, 10)
    val stabber = Gun("Stabber", 30, 4)
    val plasma = Gun("PlasmaGun", 5, 16)
    val rocket = Gun("RocketLauncher", 8, 12)

    //create Mechs
    val atlas = MechWarrior("Atlas",200, stabber, plasma)
    val madCat = MechWarrior("MadCat", 150, laser, rocket)

    //Starting fight!
    println("\n!!!БИТВА НАЧИНАЕТСЯ!!!")
    val deadOne = Duel.fight(atlas, madCat)
    println()

    println("Лут с погибшего ${deadOne.getName()}:")
    deadOne.leftGun.getAmmoInfo()
    deadOne.rightGun.getAmmoInfo()
}

//откатили main назад к initial