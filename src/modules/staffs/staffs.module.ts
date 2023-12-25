import { Module } from '@nestjs/common'

import { StaffsController } from '@staffs/staffs.controller'
import { StaffsService } from '@staffs/staffs.service'

@Module({
  controllers: [StaffsController],
  providers: [StaffsService],
})
export class StaffsModule {}
