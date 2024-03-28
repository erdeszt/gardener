package gd.services.seedcompany

import zio.*
import zio.jdbc.*

import gd.services.seedcompany.internal.SeedCompaniesRepo
import gd.services.seedcompany.model.SeedCompany

class SeedCompaniesLive(
    connection: ZConnectionPool,
    repo: SeedCompaniesRepo,
) extends SeedCompanies:
  override def list: UIO[Chunk[SeedCompany]] =
    transaction(repo.list).orDie.provide(ZLayer.succeed(connection))
