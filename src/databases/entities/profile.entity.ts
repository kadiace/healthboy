import { User } from '@entities/user.entity'

import { Column, Entity, OneToOne, PrimaryColumn } from 'typeorm'

@Entity()
export class Profile {
  @PrimaryColumn('int')
  userId!: User['id']

  @OneToOne(() => User, user => user.profile)
  user!: User

  @Column('varchar')
  firstName!: string

  @Column('varchar')
  lastName!: string

  @Column('varchar')
  imageURL!: string
}
