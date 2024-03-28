package gd.services.seedcompany.internal

import java.util.UUID

import zio.*
import zio.jdbc.*

import gd.services.seedcompany.model.SeedCompany

class SeedCompaniesRepoLive extends SeedCompaniesRepo:
  override def list: URIO[ZConnection, Chunk[SeedCompany]] =
    sql"select id, name from seed_company"
      .query[(UUID, String)]
      .as[SeedCompany]
      .selectAll
      .orDie
