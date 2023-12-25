import { Module } from '@nestjs/common'

import { RegistrationsController } from '@registrations/registrations.controller'
import { RegistrationsService } from '@registrations/registrations.service'

@Module({
  controllers: [RegistrationsController],
  providers: [RegistrationsService],
})
export class RegistrationsModule {}
