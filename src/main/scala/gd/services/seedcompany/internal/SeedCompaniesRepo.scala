package gd.services.seedcompany.internal

import zio.*
import zio.jdbc.*

import gd.services.seedcompany.model.SeedCompany

private[seedcompany] trait SeedCompaniesRepo:
  def list: URIO[ZConnection, Chunk[SeedCompany]]
