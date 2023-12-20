import { Column, Entity, PrimaryGeneratedColumn } from 'typeorm'

@Entity()
export class Gym {
  @PrimaryGeneratedColumn()
  id: number

  @Column()
  name: string
}
