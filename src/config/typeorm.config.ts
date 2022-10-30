import { TypeOrmModuleOptions } from '@nestjs/typeorm';
import { User } from '@entities/user.entity';
import { Profile } from '@entities/profile.entity';

export const typeORMConfig: TypeOrmModuleOptions = {
  type: 'mysql', //Database 설정
  host: '13.125.15.222',
  port: 3306,
  username: 'admin',
  password: 'healthboy1@',
  database: 'healthboy',
  entities: [User, Profile],
  migrations: [__dirname + '/migration/*{.ts,.js}'],
  logging: true,
  synchronize: true, //true 값을 설정하면 어플리케이션을 다시 실행할 때 엔티티안에서 수정된 컬럼의 길이 타입 변경값등을 해당 테이블을 Drop한 후 다시 생성해준다.
};
