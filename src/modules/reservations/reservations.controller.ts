import {
  Body,
  Controller,
  Delete,
  Get,
  Param,
  Patch,
  Post,
} from '@nestjs/common'

import { ReservationsService } from '@reservations/reservations.service'

@Controller('reservations')
export class ReservationsController {
  constructor(private readonly reservationsService: ReservationsService) {}

  @Post()
  create() {
    return this.reservationsService.create()
  }

  @Get()
  findAll() {
    return this.reservationsService.findAll()
  }

  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.reservationsService.findOne(+id)
  }

  @Patch(':id')
  update(@Param('id') id: string) {
    return this.reservationsService.update(+id)
  }

  @Delete(':id')
  remove(@Param('id') id: string) {
    return this.reservationsService.remove(+id)
  }
}
