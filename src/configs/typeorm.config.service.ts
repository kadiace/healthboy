import { Injectable } from '@nestjs/common'
import { ConfigService } from '@nestjs/config'
import { TypeOrmModuleOptions, TypeOrmOptionsFactory } from '@nestjs/typeorm'

import { Gym } from '@entities/gym.entity'
import { Profile } from '@entities/profile.entity'
import { Registration } from '@entities/registration.entity'
import { Reservation } from '@entities/reservation.entity'
import { Staff } from '@entities/staff.entity'
import { User } from '@entities/user.entity'

@Injectable()
export class TypeOrmConfigService implements TypeOrmOptionsFactory {
  constructor(private configService: ConfigService) {}

  createTypeOrmOptions(): TypeOrmModuleOptions {
    return {
      type: 'mysql',
      host: this.configService.get<string>('TYPEORM_HOST'),
      port: +this.configService.get<number>('TYPEORM_PORT'),
      username: this.configService.get<string>('TYPEORM_USERNAME'),
      password: this.configService.get<string>('TYPEORM_PASSWORD'),
      database: this.configService.get<string>('TYPEORM_DATABASE'),
      entities: [User, Profile, Gym, Registration, Reservation, Staff],
      migrations: [this.configService.get<string>('TYPEORM_MIGRATIONS')],
      logging: this.configService.get<boolean>('TYPEORM_LOGGING'),
      synchronize: this.configService.get<boolean>('TYPEORM_SYNCHRONIZE'),
    }
  }
}
