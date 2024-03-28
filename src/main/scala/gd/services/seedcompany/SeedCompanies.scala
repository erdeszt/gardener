package gd.services.seedcompany

import zio.*

import gd.services.seedcompany.model.SeedCompany

trait SeedCompanies:
  def list: UIO[Chunk[SeedCompany]]
