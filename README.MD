# LiquidOnline Protocol
This repository stores packages for communication with the server and client of a project such as LiquidOnline for LiquidBounce.

# Serialization and deserialization formats
- `java.awt.Color`

  Transmitted in HEX format without the leading hash

# Message formats
Messages are serialized in JSON format and contain the fields `i(d)` and `p(ayload)`.
```json
{
  "i": number,
  "p": {
    object
  }
}
```
`i(d)` - packet id

`p(ayload)` - packet data

**IMPORTANT**: @[SerializedName](https://github.com/sqlerrorthing/liquidonline-protocol/blob/master/src/main/java/fun/sqlerrorthing/liquidonline/packets/SerializedName.java) **MUST BE APPLIED** in yor json library for serialization/deserialization