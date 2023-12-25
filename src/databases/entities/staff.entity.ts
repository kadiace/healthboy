import { Gym } from '@entities/gym.entity'
import { User } from '@entities/user.entity'

import { Column, Entity, ManyToOne, PrimaryGeneratedColumn } from 'typeorm'

@Entity()
export class Staff {
  @PrimaryGeneratedColumn()
  id!: number

  /** Columns */
  @Column('varchar')
  title!: string

  /** Relations */
  @Column('int')
  userId!: User['id']

  @ManyToOne(() => User)
  user!: User

  @Column('int')
  gymId!: Gym['id']

  @ManyToOne(() => Gym)
  gym!: Gym
}
