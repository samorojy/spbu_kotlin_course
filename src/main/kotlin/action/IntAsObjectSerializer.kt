package action

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object IntAsObjectSerializer : KSerializer<Int> {
    @Serializable
    @SerialName("Int")
    data class IntSurrogate(val value: Int)

    override val descriptor: SerialDescriptor = IntSurrogate.serializer().descriptor

    override fun serialize(encoder: Encoder, value: Int) {
        IntSurrogate.serializer().serialize(encoder, IntSurrogate(value))
    }

    override fun deserialize(decoder: Decoder) =
        decoder.decodeSerializableValue(IntSurrogate.serializer()).value
}
