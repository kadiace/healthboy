import { Column, Entity, ManyToOne, PrimaryGeneratedColumn } from 'typeorm'

import { Gym } from './gym.entity'
import { Staff } from './staff.entity'
import { User } from './user.entity'

@Entity()
export class Registration {
  @PrimaryGeneratedColumn()
  id!: number

  /** Columns */
  @Column('date')
  start!: Date

  @Column('date')
  end!: Date

  /** Relations */
  @Column('int')
  customerId!: User['id']

  @ManyToOne(() => User)
  customer!: User

  @Column('int', { nullable: true })
  staffId!: Staff['id']

  @ManyToOne(() => Staff)
  staff?: Staff

  @Column('int')
  gymId!: Gym['id']

  @ManyToOne(() => Gym)
  gym!: Gym
}
