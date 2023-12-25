import {
  Body,
  Controller,
  Delete,
  Get,
  Param,
  Patch,
  Post,
} from '@nestjs/common'

import { StaffsService } from '@staffs/staffs.service'

@Controller('staffs')
export class StaffsController {
  constructor(private readonly staffsService: StaffsService) {}

  @Post()
  create() {
    return this.staffsService.create()
  }

  @Get()
  findAll() {
    return this.staffsService.findAll()
  }

  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.staffsService.findOne(+id)
  }

  @Patch(':id')
  update(@Param('id') id: string) {
    return this.staffsService.update(+id)
  }

  @Delete(':id')
  remove(@Param('id') id: string) {
    return this.staffsService.remove(+id)
  }
}
