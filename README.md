# Tom And Max

## 1) Composición del programa:
El programa consta de 26 clases, de las cuales 5 son abstractas, 
además se tienen 6 interfaces.
## 2) Funcionamiento del programa:
### I) Personajes:
Los personajes, tanto principales como enemigos, tienen en común la interface Character y la clase abstracta, AbstrctCharacter, la cual contiene los campos:
 * atk: Puntos de ataque del personaje
 * def: Puntos de defensa del personaje
 * hp: Puntos de vida del personaje
 * maxHP: La cantidad máxima de puntos de vida permitidos para el personaje
 * lvl: Nivel del personaje
 * isKO: Indica si el personaje está Knockout o no

Estos campos son privados y tienen sus propios getters y setters, a excepción de "lvl", el cual
no tiene setter como tal, sino que cambia en el método "lvlUp", el cual se encuentra en la clase abstracta AbstractMainCharacter.

Además, la clase AbstractCharacter contiene métodos utilizados por sus clases hijas para realizar ataques, estos métodos son:
* calculateDamage: Calcula el daño hecho de un personaje a otro un ataque. Esto se hace utilizando la fórmula
entregada en el enunciado (como el resultado entrega un número real, se aproxima al entero más cercano)
* receiveDamage: Recibe daño, es decir, se resta una cantidad determinada de puntos de vida.
* getKO: Evalúa si un personaje está Knockout.
* setKO: Si el personaje no está en estado K.O. pero su vida es menor o igual a 0, hace que su vida sea 0 y entre
en estado K.O.

Esta clase también implementa un método equals.

De la clase Character se desprenden otras 2 clases abstractas las cuales son Player y Enemy.

### I.a) Personajes principales.
Como se nombró anteriormente, existe la clase abstracta AbstractMainCharacter, la cual tiene atributos y métodos necesarios para representar a los personajes principales del juego.
La clase tiene los siguientes campos:
* fp: Puntos de pelea actuales
* maxFP: Puntos de pelea máximos

Estos campos tienen sus propios getters y setters.
Además, se tienen métodos para recuperar fp y hp, los cuales se llaman recoverFP y recoverHP respectivamente, estos
métodos son activados al consumir un item utilizando el método useItem. Al utilizar este último método, dependiendo
del item utilizado, se llama a uno de los siguientes métodos.
* healingEffect: Llama al método recoverHP, haciendo el personaje recupere 10% de su vida máxima.
* fightingEffect: Llama al método recoverFP, haciendo el personaje recupere 3 puntos de pelea.

Además, esta clase implementa los métodos equals y hashCode.

Esta clase tiene como subclases las clases Tom y Max, las cuales a su vez implementan la interface MainCharacter (con la finalidad de hacer los llamados a esas clases).

### I.b) Enemigos
Los enemigos son representados mediante la clases AbstractEnemy y AbstractImmuneEnemy, la primera de estas agrega el campo "name", el cual
es usado para diferenciar los enemigos mediante el método equals (hay un método setName, el cual es usado en los
constructores de las clases hijas).

Por otro lado, existen 2 interfaces para hacer los llamados a los enemigos dependiendo de si estos son inmunes a ciertos tipos de ataques o no. Los enemigos que tienen inmunidad implementan la interface ImmuneEnemy, mientras que los enemigos "normales" implementarían la interface Enemy.

La interface Enemy, contiene métodos auxiliares para:
* Saber si un tipo de enemigo puede atacar o ser atacado por cierto personaje principal
* 
Y la interface ImmuneEnemy contiene métodos para:
* Saber si es inmune o no a cierto tipo de ataque
* Provocarle un efecto al personaje principal atacante, si es que es inmune al ataque recibido

Notar que, la clase abstracta AbstractImmuneEnemy es hija de la clase Enemy y la interfaz ImmuneEnemy extiende a la interfaz Enemy.
Además:
* La clase Slime implementa la interfaz Enemy
* Las clases Ghost y Thorny implementan la interfaz ImmuneEnemy.

### II) Ataques
Los ataques del jugador se implementaron creando la clase abstracta "AbstractAttack", la cual contiene los siguientes campos:
* costFP: Costo que tiene el ataque en puntos de pelea
* k: La constante utilizada para calcular el daño
* hitProbability: La probabilidad de acierto del ataque (entre 0 y 1)

(Para acceder a estos campos se crearon sus respectivos getters).

También se tiene la interfaz Attack, la cual tiene un método para indicar si el ataque en cuestión es inútil ante un tipo de enemigo, este
método se llama isUselessTo y es implementado para cada clase que implementa la interfaz (las clases Salto y Martillo).

### III) Items
Para acceder a los items se creó la clase Chest, la cual representa un baúl (inventario) utilizado por Tom y Max.
Este cofre tiene el campo "items", el cual corresponde a una Hashtable cuya llave es el nombre del Item (String) y el valor es la
es un par de la forma <Item, ItemQuantity>, donde:
* Item es una interfaz que representa los distintos tipos de items.
* ItemQuantity es una clase que permite manejar la cantidad de objetos de un tipo que hay en el baúl.

Esta clase implementa métodos para agregar items a la mochila (addItems) y para consumir un item (consumeItem).
(Además se implementan los métodos equals y hashCode).

Como se expuso anteriormente, se creó la interfaz Item representar los distintos tipos de items. Esta interfaz es implementada por las clases HealingPotion y FightingPotion, las cuales deben implementar el método "effect", cuya función es aplicar el efecto que tiene el item en el jugador.

La clase ItemQuantity, la cual es utilizada por la clase Chest para llevar un conteo de cuantos
objetos se tienen en la mochila. Los métodos son:
* add: Aumenta el contador del item en una cantidad dada por el usuario del método
* takeOne: Disminuye en 1 el contador del Item
* thereAre: Arroja verdadero cuando el contador es mayor a 0 (hay objetos de ese tipo)
* getQuantity: Arroja la cantidad de items

### IV) Controlador
Es una clase que contiene muchos métodos para regular el desarrollo del juego.
Permite:
• Crear a los personajes principales. (en el método setUp)
• Crear a los enemigos. (en el método setUp)
• Crear a los Items. (en el método createItems)
• Crear el baúl de los personajes principales. (en el método createChest)
• Implementar los turnos. (mediante el uso de las fases y los métodos try)
• Que un jugador pueda utilizar un elemento al baúl. (mediante el método tryToUseItem)
• Obtener los elementos del baúl. (en el método getChestItems)
• Obtener todos los personajes del turno. (en los métodos getMcList y getEnemiesList)
• Quitar a un personaje del “turno” cuando está K.O. (en los métodos eraseKOMC y eraseKOEnemy)
• Saber cuando los personajes principales ganan o pierden. (en los métodos won y lost)
• Obtener el personaje que posee el turno actual. (en los métodos getTurnOwnerName y getCurrentTurn)
• Obtener el personaje del siguiente turno. (en el método getNextTurn)
• Terminar el turno del jugador actual. (en los métodos endTurn tryToGoToNextTurn)

### V) Fases
Permiten el desarrollo del flujo del juego 
Hay un clase Phase que engloba a las 11 fases usables, las cuales son:
• WaitActionPhase         : En esta fase se elige qué hacer (atacar, usar item, pasar el turno)
• AttackSelectionPhase    : En esta fase, el usuario elige un ataque para atacar a un enemigo
• EnemySelectionPhase     : En esta fase, el usuario elige el enemigo a atacar
• AttackPhase             : En esta fase, el personaje principal ataca
• ItemSelectionPhase      : En esta fase, el usuario elige un item para utilizar
• MCSelectionPhase        : En esta fase, el usuario elige un el personaje principal que utilizará el item
• UseItemPhase            : En esta fase, se usa el item
• NextTurnChoosingPhase   : En esta fase, se elige de quién será el siguiente turno (personaje principal o enemigo)
• EnemiesPhase            : Esta fase es la que desarrolla el turno de algún enemigo
• WinPhase                : Al ganar se pasa a esta fase
• LossPhase               : Al perder se pasa a esta fase
### VI) Excepciones
Se crearon 4, las cuales permiten manejar los errores

### VII) GUI
Permite utilizar el juego de manera gráfica.
Se creó utilizando javafx.

