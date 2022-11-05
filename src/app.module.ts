import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ConfigModule } from '@nestjs/config';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { typeORMConfig } from './config/typeorm.config';
import { GymsModule } from './modules/gyms/gyms.module';
import { UsersModule } from './modules/users/users.module';

@Module({
  imports: [ConfigModule.forRoot({
    envFilePath: `${__dirname}/../.env.${process.env.NODE_ENV}`,
    isGlobal: true,
  }),TypeOrmModule.forRoot(typeORMConfig),  UsersModule, GymsModule],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule { }
