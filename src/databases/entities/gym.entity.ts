import { User } from '@entities/user.entity'

import {
  Column,
  Entity,
  JoinTable,
  ManyToMany,
  PrimaryGeneratedColumn,
} from 'typeorm'

@Entity()
export class Gym {
  @PrimaryGeneratedColumn()
  id!: number

  /** Columns */
  @Column('varchar')
  name!: string

  /** Relations */
  @ManyToMany(() => User)
  @JoinTable()
  owners!: User[]
}
