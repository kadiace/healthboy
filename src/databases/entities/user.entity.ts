import { Profile } from '@entities/profile.entity'

import { Column, Entity, OneToOne, PrimaryGeneratedColumn } from 'typeorm'

@Entity()
export class User {
  @PrimaryGeneratedColumn()
  id!: number

  @Column('varchar')
  email!: string

  @Column('varchar')
  password!: string

  @OneToOne(() => Profile, profile => profile.user)
  profile!: Profile
}
