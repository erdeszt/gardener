package gd.services.seedcompany

import gd.services.seedcompany.internal.SeedCompaniesRepo
import gd.services.seedcompany.model.SeedCompany
import zio.jdbc.*

class SeedCompaniesRepoLive extends SeedCompaniesRepo:
  override def list: Unit =
    sql"select id, name from seed_company"
      .query[(SeedCompany.Id, SeedCompany.Name)]
      .selectAll
