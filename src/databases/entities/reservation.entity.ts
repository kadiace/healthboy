import { Staff } from '@entities/staff.entity'
import { User } from '@entities/user.entity'

import { Column, Entity, ManyToOne, PrimaryGeneratedColumn } from 'typeorm'

import { Gym } from './gym.entity'

@Entity()
export class Reservation {
  @PrimaryGeneratedColumn()
  id!: number

  /** Columns */
  @Column('timestamp')
  start!: Date

  @Column('timestamp')
  end!: Date

  /** Relations */
  @Column('int')
  customerId!: User['id']

  @ManyToOne(() => User)
  customer!: User

  @Column('int')
  staffId!: Staff['id']

  @ManyToOne(() => Staff)
  staff!: Staff

  @Column('int')
  gymId!: Gym['id']

  @ManyToOne(() => Gym)
  gym!: Gym
}
