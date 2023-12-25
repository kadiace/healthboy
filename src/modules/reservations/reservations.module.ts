import { Module } from '@nestjs/common'

import { ReservationsController } from '@reservations/reservations.controller'
import { ReservationsService } from '@reservations/reservations.service'

@Module({
  controllers: [ReservationsController],
  providers: [ReservationsService],
})
export class ReservationsModule {}
