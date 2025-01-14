package net.mamoe.mirai.contact


fun Int.asQQ(): QQ = Instances.qqs.stream().filter { t: QQ? -> t?.number?.equals(this)!! }.findAny().orElse(QQ(this))!!

fun Int.asGroup(): Group = Instances.groups.stream().filter { t: Group? -> t?.number?.equals(this)!! }.findAny().orElse(Group(this))!!

fun String.withImage(id: String, type: String) = "{$id}.$type"

fun String.withAt(qq: Int) = qq.asQQ().at()

fun String.withAt(qq: QQ) = qq.at()

object Instances {
    var qqs = arrayListOf<QQ>()
    var groups = arrayListOf<Group>()
}
