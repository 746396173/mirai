package net.mamoe.mirai.network.packet.client.login

import net.mamoe.mirai.network.Protocol
import net.mamoe.mirai.network.packet.PacketId
import net.mamoe.mirai.network.packet.client.ClientPacket
import net.mamoe.mirai.network.packet.client.writeHex
import net.mamoe.mirai.network.packet.client.writeIP
import net.mamoe.mirai.network.packet.client.writeQQ
import net.mamoe.mirai.util.ByteArrayDataOutputStream
import net.mamoe.mirai.util.TEACryptor
import net.mamoe.mirai.util.hexToBytes
import java.io.IOException

/**
 * Server redirection (0825 response)
 *
 * @author Him188moe
 */
@ExperimentalUnsignedTypes
@PacketId("08 25 31 02")
class ClientServerRedirectionPacket(private val serverIP: String, private val qq: Int) : ClientPacket() {
    @ExperimentalUnsignedTypes
    override fun encode() {
        this.writeQQ(qq)
        this.writeHex(Protocol.fixVer)
        this.writeHex(Protocol.redirectionKey)


        this.write(TEACryptor.encrypt(object : ByteArrayDataOutputStream() {
            @Throws(IOException::class)
            override fun toByteArray(): ByteArray {
                this.writeHex(Protocol._0825data0)
                this.writeHex(Protocol._0825data2)
                this.writeQQ(qq)
                this.writeHex("00 01 00 00 03 09 00 0C 00 01")
                this.writeIP(serverIP)
                this.writeHex("01 6F A1 58 22 01 00 36 00 12 00 02 00 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 14 00 1D 01 03 00 19")
                this.writeHex(Protocol.publicKey)
                return super.toByteArray()
            }
        }.toByteArray(), Protocol.redirectionKey.hexToBytes()))
    }
}