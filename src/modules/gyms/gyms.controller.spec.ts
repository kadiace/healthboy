import { Test, TestingModule } from '@nestjs/testing'

import { GymsController } from '@gyms/gyms.controller'
import { GymsService } from '@gyms/gyms.service'

describe('GymsController', () => {
  let controller: GymsController

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      controllers: [GymsController],
      providers: [GymsService],
    }).compile()

    controller = module.get<GymsController>(GymsController)
  })

  it('should be defined', () => {
    expect(controller).toBeDefined()
  })
})
