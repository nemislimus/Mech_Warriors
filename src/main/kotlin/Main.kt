
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

    private fun reloadAmmo() {
        ammoCount = ammo
    }

    fun getAmmoInfo() {
        println("Пушка $model. Осталось зарядов: $ammoCount")
    }

    init {
        this.reloadAmmo()
    }
}

class MechWarrior(private val name: String, private var healthPoint: Int, val firstGun: Gun, val secondGun: Gun) {

    var isAlive: Boolean = true

    fun attackEnemy(enemy: MechWarrior) {
        println("!!!!!$name атакует ${enemy.name}!!!!!")
        enemy.takeDamage(firstGun.fire())
        enemy.takeDamage(secondGun.fire())
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
                        striker.attackEnemy(defender)
                        if (!stillAlive(defender)) return defender
                    } else continue
                }
            }
            round++
            Thread.sleep(1000)
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
    deadOne.secondGun.getAmmoInfo()
    deadOne.firstGun.getAmmoInfo()
}



//const val ANSI_RESET = "\u001B[0m"
//const val ANSI_YELLOW = "\u001B[33m"
//const val ANSI_RED = "\u001B[31m"
//const val ANSI_GREEN = "\u001B[32m"

//// ANSI Escape коды для установки цвета текста
//val red = "\u001B[31m"
//val green = "\u001B[32m"
//val blue = "\u001B[34m"
//val reset = "\u001B[0m" // Сброс цвета обратно к стандартному
//
//// Примеры текста с разными цветами
//println("${red}Красный текст${reset}")
//println("${green}Зеленый текст${reset}")
//println("${blue}Синий текст${reset}")

//Черный (\u001B[30m)
//Красный (\u001B[31m)
//Зеленый (\u001B[32m)
//Желтый (\u001B[33m)
//Синий (\u001B[34m)
//Маджента (пурпурный) (\u001B[35m)
//Циан (голубой) (\u001B[36m)
//Белый (\u001B[37m)



//create commit for reset testing. START_SPOT