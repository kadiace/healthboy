import { Module } from '@nestjs/common'
import { ConfigModule } from '@nestjs/config'
import { TypeOrmModule } from '@nestjs/typeorm'

import { TypeOrmConfigService } from '@configs/typeorm.config.service'

import { GymsModule } from '@gyms/gyms.module'

import { ProfilesModule } from '@profiles/profiles.module'

import { RegistrationsModule } from '@registrations/registrations.module'

import { ReservationsModule } from '@reservations/reservations.module'

import { StaffsModule } from '@staffs/staffs.module'

import { UsersModule } from '@users/users.module'

import { AppController } from './app.controller'
import { AppService } from './app.service'

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
      envFilePath: `.env.${process.env.NODE_ENV}`,
    }),
    TypeOrmModule.forRootAsync({
      imports: [ConfigModule],
      useClass: TypeOrmConfigService,
    }),
    UsersModule,
    GymsModule,
    StaffsModule,
    ReservationsModule,
    RegistrationsModule,
    ProfilesModule,
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
