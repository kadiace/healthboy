import {
  Body,
  Controller,
  Delete,
  Get,
  Param,
  Patch,
  Post,
} from '@nestjs/common'

import { GymsService } from '@gyms/gyms.service'

@Controller('gyms')
export class GymsController {
  constructor(private readonly gymsService: GymsService) {}

  @Post()
  create() {
    return this.gymsService.create()
  }

  @Get()
  findAll() {
    return this.gymsService.findAll()
  }

  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.gymsService.findOne(+id)
  }

  @Patch(':id')
  update(@Param('id') id: string) {
    return this.gymsService.update(+id)
  }

  @Delete(':id')
  remove(@Param('id') id: string) {
    return this.gymsService.remove(+id)
  }
}
