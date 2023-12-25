import { Module } from '@nestjs/common'

import { GymsController } from '@gyms/gyms.controller'
import { GymsService } from '@gyms/gyms.service'

@Module({
  controllers: [GymsController],
  providers: [GymsService],
})
export class GymsModule {}
