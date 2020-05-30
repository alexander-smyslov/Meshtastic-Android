package com.geeksville.mesh

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
enum class MessageStatus : Parcelable {
    UNKNOWN, // Not set for this message
    RECEIVED, // Came in from the mesh
    QUEUED, // Waiting to send to the mesh as soon as we connect to the device
    ENROUTE, // Delivered to the radio, but no ACK or NAK received
    DELIVERED // We received an ack
}

/**
 * A parcelable version of the protobuf MeshPacket + Data subpacket.
 */
@Serializable
@Parcelize
data class DataPacket(
    val from: String, // a nodeID string
    val to: String, // a nodeID string
    val rxTime: Long, // msecs since 1970
    val id: Int,
    val dataType: Int,
    val bytes: ByteArray,
    val status: MessageStatus = MessageStatus.UNKNOWN
) : Parcelable {

    // Autogenerated comparision, because we have a byte array
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DataPacket

        if (from != other.from) return false
        if (to != other.to) return false
        if (rxTime != other.rxTime) return false
        if (id != other.id) return false
        if (dataType != other.dataType) return false
        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = from.hashCode()
        result = 31 * result + to.hashCode()
        result = 31 * result + rxTime.hashCode()
        result = 31 * result + id
        result = 31 * result + dataType
        result = 31 * result + bytes.contentHashCode()
        return result
    }
}