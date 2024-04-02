package gd.services.seedcompany.internal

import com.github.f4b6a3.ulid.Ulid
import zio.*
import zio.jdbc.*

import gd.services.seedcompany.model.SeedCompany

private[seedcompany] trait SeedCompaniesRepo:
  def list: URIO[ZConnection, Chunk[SeedCompany]]
  def insert(name: SeedCompany.Name): URIO[ZConnection, SeedCompany.Id]
