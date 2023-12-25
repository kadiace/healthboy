import {
  Body,
  Controller,
  Delete,
  Get,
  Param,
  Patch,
  Post,
} from '@nestjs/common'

import { RegistrationsService } from '@registrations/registrations.service'

@Controller('registrations')
export class RegistrationsController {
  constructor(private readonly registrationsService: RegistrationsService) {}

  @Post()
  create() {
    return this.registrationsService.create()
  }

  @Get()
  findAll() {
    return this.registrationsService.findAll()
  }

  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.registrationsService.findOne(+id)
  }

  @Patch(':id')
  update(@Param('id') id: string) {
    return this.registrationsService.update(+id)
  }

  @Delete(':id')
  remove(@Param('id') id: string) {
    return this.registrationsService.remove(+id)
  }
}
