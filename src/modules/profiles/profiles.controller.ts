import {
  Body,
  Controller,
  Delete,
  Get,
  Param,
  Patch,
  Post,
} from '@nestjs/common'

import { ProfilesService } from '@profiles/profiles.service'

@Controller('profiles')
export class ProfilesController {
  constructor(private readonly profilesService: ProfilesService) {}

  @Post()
  create() {
    return this.profilesService.create()
  }

  @Get()
  findAll() {
    return this.profilesService.findAll()
  }

  @Get(':id')
  findOne(@Param('id') id: string) {
    return this.profilesService.findOne(+id)
  }

  @Patch(':id')
  update(@Param('id') id: string) {
    return this.profilesService.update(+id)
  }

  @Delete(':id')
  remove(@Param('id') id: string) {
    return this.profilesService.remove(+id)
  }
}
