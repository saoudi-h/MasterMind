import kotlin.system.exitProcess

class MasterMind {
    companion object {
        private val COLORS = listOf("blanc", "jaune", "orange", "vert", "rose", "rouge", "violet", "bleu", "noir")
        private val numberColor = 4
    }
    private var gameAttempts = 10
    private var userSelection:MutableList<String> = mutableListOf()
    private var selection:MutableList<String> = mutableListOf()

    fun genRandomColorList():List<String>{
        return COLORS.shuffled().subList(0,numberColor)
    }
    fun readColors(): MutableList<String> {
        var attempt = 3
        while (attempt > 0) {
            println("-".repeat(90))
            println("Veuillez saisir $numberColor couleurs séparées par des espaces parmi la selection suivante :")
            println(COLORS)
            println("-".repeat(90))
            println("")
            val inputLine = readLine()
            val inputListColors = if (inputLine != null) inputLine.split(" ").toMutableList() else mutableListOf()
            if (!isValidList(inputListColors)) {
                println("Erreur de saisie, encore $attempt tentative${if (attempt == 1) "" else "s"}")
                attempt--
                continue
            }
            return inputListColors
        }

        if (attempt == 0) {
            println("Trop de tentatives.")
            printLose()
            exitProcess(0)
        }

        return mutableListOf()
    }

    private fun isValidList(listColors:MutableList<String>):Boolean{
        if(listColors.count() != numberColor) return false
        for(color in listColors){
            if(color !in COLORS) return false
        }
        return true
    }
    private fun evaluate():List<Int>{
        var i=0
        var res:MutableList<Int> = mutableListOf()
        for(color in userSelection){
            res.add(
                when{
                    color !in selection -> 2
                    color != selection.get(i) ->1
                    else-> 0
                })
            i++
        }
        return res
    }

    private fun info(code:Int):String{
        return  when(code){
            2 -> "NO"
            1 -> "EXIST"
            else-> "OK"
        }
    }
    private fun printInfos(codes:List<Int>){
        userSelection.forEachIndexed{index,color->
            println("   $color  :   ${info(codes[index])}")
        }
    }
    fun checkWin(res:List<Int>):Boolean {
        return res.sum()==0
    }
    private fun printLose(){
        println("\n" +
                " .---.   .--.  .-.   .-..----.    .----. .-. .-..----..----. \n" +
                "/   __} / {} \\ |  `.'  || {_     /  {}  \\| | | || {_  | {}  }\n" +
                "\\  {_ }/  /\\  \\| |\\ /| || {__    \\      /\\ \\_/ /| {__ | .-. \\\n" +
                " `---' `-'  `-'`-' ` `-'`----'    `----'  `---' `----'`-' `-'\n")
    }
    private fun printWin(){
       println("\n" +
               "__  __                        _     \n" +
               "\\ \\/ /___  __  __   _      __(_)___ \n" +
               " \\  / __ \\/ / / /  | | /| / / / __ \\\n" +
               " / / /_/ / /_/ /   | |/ |/ / / / / /\n" +
               "/_/\\____/\\__,_/    |__/|__/_/_/ /_/ \n" +
               "                                    \n")
    }
    private fun printLogo(){
        println("\n" +
                "  __  __           _            __  __ _           _ \n" +
                " |  \\/  |         | |          |  \\/  (_)         | |\n" +
                " | \\  / | __ _ ___| |_ ___ _ __| \\  / |_ _ __   __| |\n" +
                " | |\\/| |/ _` / __| __/ _ \\ '__| |\\/| | | '_ \\ / _` |\n" +
                " | |  | | (_| \\__ \\ ||  __/ |  | |  | | | | | | (_| |\n" +
                " |_|  |_|\\__,_|___/\\__\\___|_|  |_|  |_|_|_| |_|\\__,_|\n" +
                "                                                     \n" +
                "                                                     \n")
    }

    fun run(){
        printLogo()
        selection = genRandomColorList().toMutableList()
        while(gameAttempts>0){
//            println("selection : "+ selection)
            userSelection = readColors()
            var res = evaluate()
            if(checkWin(res)){
                printWin()
                break
            }else {
                gameAttempts--
                printInfos(res)
                println("")
                println("il vous reste encore $gameAttempts tentative${if (gameAttempts == 1) "" else "s"}")
                println("")
                if(gameAttempts==0){
                    printLose()
                    break
                }
            }
        }
    }
}