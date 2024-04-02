package gd.interop

import com.github.f4b6a3.ulid.Ulid
import zio.Chunk
import zio.jdbc.*
import zio.schema.Schema

object ulid:

  given ulidSchema: Schema[Ulid] = Schema[Chunk[Byte]].transform(
    chunks => Ulid.from(chunks.toArray),
    ulid => Chunk.fromArray(ulid.toBytes),
  )

  // TODO: Avoid roundtrip with String
  given ulidDecoder: JdbcDecoder[Ulid] =
    JdbcDecoder[Ulid](result =>
      i => Ulid.from(new String(result.getBytes(i), "UTF-8")),
    )

  given ulidEncoder: JdbcEncoder[Ulid] =
    JdbcEncoder[Ulid](value => sql"${value.toString.getBytes("UTF-8")}")
